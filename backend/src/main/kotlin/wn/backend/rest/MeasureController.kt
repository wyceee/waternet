package wn.backend.rest

import MeasureDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wn.backend.models.Measure
import wn.backend.repositories.MeasureRepositoryJPA
import wn.backend.repositories.UserRepositoryJPA

@RestController
@RequestMapping("/api/measures")
class MeasureController(
    private val measureRepositoryJPA: MeasureRepositoryJPA,
    private val userRepositoryJPA: UserRepositoryJPA
) {

    // Get all measures
    @GetMapping
    fun getAllMeasures(): ResponseEntity<List<Measure>> {
        val measures = measureRepositoryJPA.findAll()
        return ResponseEntity.ok(measures)
    }

    // Get measure by ID
    @GetMapping("/{id}")
    fun getMeasureById(@PathVariable id: Long): ResponseEntity<Measure> {
        val measure = measureRepositoryJPA.findById(id)
        return if (measure.isPresent) {
            ResponseEntity.ok(measure.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Get all measures by user ID
    @GetMapping("/user/{userId}")
    fun getMeasuresByUserId(@PathVariable userId: Long): ResponseEntity<List<Measure>> {
        val measures = measureRepositoryJPA.findAllByUserId(userId)
        return ResponseEntity.ok(measures)
    }

    @PostMapping
    fun submitMeasure(@RequestBody dto: MeasureDTO): ResponseEntity<Measure> {
        val user = userRepositoryJPA.findById(dto.userId).orElse(null)
            ?: return ResponseEntity.badRequest().build()

        val measure = Measure(
            user = user,
            description = dto.description,
            photoUrl = dto.photoUrl,
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

    // Approve a measure
    @PostMapping("/{id}/approve")
    fun approveMeasure(@PathVariable id: Long): ResponseEntity<String> {
        return if (measureRepositoryJPA.approve(id)) {
            ResponseEntity.ok("Measure approved successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to approve measure.")
        }
    }

    // Reject a measure
    @PostMapping("/{id}/reject")
    fun rejectMeasure(@PathVariable id: Long): ResponseEntity<String> {
        return if (measureRepositoryJPA.reject(id)) {
            ResponseEntity.ok("Measure rejected successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to reject measure.")
        }
    }
}