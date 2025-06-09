package wn.backend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import wn.backend.models.Measure
import java.util.Optional

@Repository
interface MeasureRepository : JpaRepository<Measure, Long> {

    // Find all measures
    override fun findAll(): List<Measure>

    // Find measure by ID
    override fun findById(id: Long): Optional<Measure>

    // Find all measures by user ID
    fun findAllByUserId(userId: Long): List<Measure>

    // Approve a pending measure
    @Modifying
    @Transactional
    @Query("UPDATE Measure m SET m.status = 'APPROVED' WHERE m.id = :id AND m.status = 'PENDING'")
    fun approve(id: Long): Int

    // Reject a pending measure
    @Modifying
    @Transactional
    @Query("UPDATE Measure m SET m.status = 'REJECTED' WHERE m.id = :id AND m.status = 'PENDING'")
    fun reject(id: Long): Int
}