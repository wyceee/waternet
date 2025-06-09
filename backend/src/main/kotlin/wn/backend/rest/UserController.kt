package wn.backend.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wn.backend.models.Role
import wn.backend.models.User
import wn.backend.repositories.UserRepositoryJPA

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepositoryJPA: UserRepositoryJPA
) {

    // Get all users
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userRepositoryJPA.findAll()
        return ResponseEntity.ok(users)
    }

    // Get user by ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userRepositoryJPA.findById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Create a new user
    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userRepositoryJPA.createUser(user)
        return ResponseEntity.ok(createdUser)
    }

    // Change the role of a user
    @PutMapping("/{id}/role")
    fun changeUserRole(@PathVariable id: Long, @RequestParam role: Role): ResponseEntity<String> {
        return if (userRepositoryJPA.changeRole(id, role)) {
            ResponseEntity.ok("User role updated successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to update user role.")
        }
    }
}