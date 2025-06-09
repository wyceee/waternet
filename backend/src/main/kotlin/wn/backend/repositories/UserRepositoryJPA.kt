package wn.backend.repositories

import org.springframework.stereotype.Repository
import wn.backend.models.Role
import wn.backend.models.User
import java.util.Optional

@Repository
class UserRepositoryJPA(
    private val userRepository: UserRepository
) {

    // Find all users
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    // Find user by ID
    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    // Create a new user
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    // Change the role of a user
    fun changeRole(id: Long, role: Role): Boolean {
        return userRepository.changeRole(id, role) > 0
    }
}