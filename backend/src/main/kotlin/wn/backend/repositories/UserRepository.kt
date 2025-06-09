package wn.backend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import wn.backend.models.Role
import wn.backend.models.User
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {

    override fun findAll(): List<User>

    override fun findById(id: Long): Optional<User>

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    fun changeRole(id: Long, role: Role): Int
}