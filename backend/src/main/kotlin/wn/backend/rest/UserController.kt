package wn.backend.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wn.backend.models.Role
import wn.backend.models.User
import wn.backend.repositories.UserRepositoryJPA
import wn.backend.sepolia.ContractService
import java.math.BigInteger

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepositoryJPA: UserRepositoryJPA,
    private val contractService: ContractService
) {

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

    @GetMapping("/{id}/balance")
    fun getUserBalance(@PathVariable id: Long): ResponseEntity<BigInteger> {
        val user = userRepositoryJPA.findById(id)
        return if (user.isPresent) {
            val wallet = user.get().wallet
            val balance = contractService.balanceOf(wallet)
            ResponseEntity.ok(balance)
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