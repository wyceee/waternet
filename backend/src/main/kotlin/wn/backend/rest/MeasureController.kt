package wn.backend.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wn.backend.models.Measure
import wn.backend.repositories.MeasureRepositoryJPA

@RestController
@RequestMapping("/api/measures")
class MeasureController(
    private val measureRepositoryJPA: MeasureRepositoryJPA
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