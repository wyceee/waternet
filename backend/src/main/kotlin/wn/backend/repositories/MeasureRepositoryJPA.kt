package wn.backend.repositories

import org.springframework.stereotype.Repository
import wn.backend.models.Measure
import java.util.Optional

@Repository
class MeasureRepositoryJPA(
    private val measureRepository: MeasureRepository
) {

    fun findAll(): List<Measure> {
        return measureRepository.findAll()
    }

    fun findById(id: Long): Optional<Measure> {
        return measureRepository.findById(id)
    }

    fun findAllByUserId(userId: Long): List<Measure> {
        return measureRepository.findAllByUserId(userId)
    }

    fun save(measure: Measure): Measure {
        return measureRepository.save(measure)
    }

    fun approve(id: Long): Boolean {
        return measureRepository.approve(id) > 0
    }

    fun reject(id: Long): Boolean {
        return measureRepository.reject(id) > 0
    }
}