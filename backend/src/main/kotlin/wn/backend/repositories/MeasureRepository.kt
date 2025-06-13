package wn.backend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wn.backend.models.Measure
import java.util.Optional

@Repository
interface MeasureRepository : JpaRepository<Measure, Long> {

    override fun findAll(): List<Measure>

    override fun findById(id: Long): Optional<Measure>

    fun findAllByUserId(userId: Long): List<Measure>

    fun save (measure: Measure): Measure
}