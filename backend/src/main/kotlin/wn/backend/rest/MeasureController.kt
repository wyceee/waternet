package wn.backend.rest

import MeasureDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wn.backend.models.Measure
import wn.backend.repositories.MeasureRepositoryJPA
import wn.backend.repositories.UserRepositoryJPA
import wn.backend.sepolia.ContractService
import java.math.BigInteger
import java.util.Base64
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Instant

@RestController
@RequestMapping("/api/measures")
class MeasureController(
    private val measureRepositoryJPA: MeasureRepositoryJPA,
    private val userRepositoryJPA: UserRepositoryJPA,
    private val contractService: ContractService
) {

    @GetMapping
    fun getAllMeasures(): ResponseEntity<List<Measure>> {
        val measures = measureRepositoryJPA.findAll()
        return ResponseEntity.ok(measures)
    }

    @GetMapping("/{id}")
    fun getMeasureById(@PathVariable id: Long): ResponseEntity<Measure> {
        val measure = measureRepositoryJPA.findById(id)
        return if (measure.isPresent) {
            ResponseEntity.ok(measure.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/user/{userId}")
    fun getMeasuresByUserId(@PathVariable userId: Long): ResponseEntity<List<Measure>> {
        val measures = measureRepositoryJPA.findAllByUserId(userId)
        return ResponseEntity.ok(measures)
    }

    @PostMapping
    fun submitMeasure(@RequestBody dto: MeasureDTO): ResponseEntity<Measure> {
        val user = userRepositoryJPA.findById(dto.userId).orElse(null)
            ?: return ResponseEntity.badRequest().build()

        val base64Data = dto.photoUrl.substringAfter("base64,")
        val imageBytes = Base64.getDecoder().decode(base64Data)

        val fileName = "${System.currentTimeMillis()}.png"
        val imagePath = Paths.get("uploads/images").resolve(fileName)
        Files.createDirectories(imagePath.parent)
        Files.write(imagePath, imageBytes, StandardOpenOption.CREATE)

        val measure = Measure(
            user = user,
            description = dto.description,
            photoUrl = "/uploads/images/$fileName",
            location = dto.location,
            timestamp = dto.timestamp,
            status = dto.status,
            measureType = dto.measureType,
            area = dto.area,
            capacity = dto.capacity
        )

        val saved = measureRepositoryJPA.save(measure)
        return ResponseEntity.ok(saved)
    }

    @PostMapping("/{id}/approve")
    fun approveMeasure(@PathVariable id: Long): ResponseEntity<String> {
        val measure = measureRepositoryJPA.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        if (measure.status != Measure.Status.PENDING) {
            return ResponseEntity.badRequest().body("Only pending measures can be approved.")
        }

        measure.status = Measure.Status.APPROVED
        // Transfer 100 AQR tokens to the user's wallet
        val user = measure.user
        val walletAddress = user.wallet
        return try {
            val receipt = contractService.transfer(walletAddress, BigInteger.valueOf(100).multiply(BigInteger.TEN.pow(18)))
            val txHash = receipt.transactionHash
            measure.txHash = txHash // Save txHash
            measureRepositoryJPA.save(measure)
            ResponseEntity.ok("Measure approved and 100 AQR tokens transferred. TX:$txHash")
        } catch (e: Exception) {
            measureRepositoryJPA.save(measure)
            ResponseEntity.internalServerError().body("Measure approved, but token transfer failed: ${e.message}")
        }
    }

    @PostMapping("/{id}/reject")
    fun rejectMeasure(@PathVariable id: Long): ResponseEntity<String> {
        val measure = measureRepositoryJPA.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        if (measure.status != Measure.Status.PENDING) {
            return ResponseEntity.badRequest().body("Only pending measures can be rejected.")
        }

        measure.status = Measure.Status.REJECTED
        measureRepositoryJPA.save(measure)
        return ResponseEntity.ok("Measure rejected successfully.")
    }

    @GetMapping("/user/{userId}/rewards")
    fun getUserOnChainRewards(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val user = userRepositoryJPA.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().build()

        val wallet = user.wallet
        val allTransfers = contractService.getOnChainTransfersToUser(wallet)
            .sortedByDescending { it.timestamp }

        val start = page * size
        val end = (start + size).coerceAtMost(allTransfers.size)
        val pageTransfers = if (start >= allTransfers.size) emptyList() else allTransfers.subList(start, end)

        val content = pageTransfers.map {
            mapOf(
                "title" to "On-Chain Reward",
                "date" to it.timestamp,
                "tx" to it.tx,
                "amount" to it.amount.divide(BigInteger.TEN.pow(18)).toInt()
            )
        }

        return ResponseEntity.ok(
            mapOf(
                "content" to content,
                "totalElements" to allTransfers.size
            )
        )
    }

}