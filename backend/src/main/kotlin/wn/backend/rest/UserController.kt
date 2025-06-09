package wn.backend.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wn.backend.models.Role
import wn.backend.models.User
import wn.backend.repositories.UserRepositoryJPA

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepositoryJPA: UserRepositoryJPA) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userRepositoryJPA.findAll()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userRepositoryJPA.findById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}/role")
    fun changeUserRole(@PathVariable id: Long, @RequestParam role: Role): ResponseEntity<String> {
        return if (userRepositoryJPA.changeRole(id, role)) {
            ResponseEntity.ok("User role updated successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to update user role.")
        }
    }
}