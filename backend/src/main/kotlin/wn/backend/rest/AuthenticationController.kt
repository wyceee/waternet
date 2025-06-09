package wn.backend.rest

import com.fasterxml.jackson.databind.node.ObjectNode
import wn.backend.APIConfig
import wn.backend.models.User
import wn.backend.repositories.UserRepository
import wn.backend.utils.JWToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/authentication")
class AuthenticationController {

    @Autowired
    private lateinit var apiConfig: APIConfig

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: ObjectNode): ResponseEntity<User> {
        val email = loginRequest["email"].asText()
        val password = loginRequest["password"].asText()

        val user = userRepository.findAll().find { it.email == email }

        if (user == null || !passwordEncoder.matches(password, user.hashedPassword)) {
            return ResponseEntity.status(401).build()
        }

        val token = JWToken(user.name, user.id!!, user.role)
        val tokenString = token.encode(
            apiConfig.issuer,
            apiConfig.passPhrase ?: "",
            apiConfig.tokenDurationOfValidity
        )

        return ResponseEntity.accepted()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $tokenString")
            .body(user)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: ObjectNode): ResponseEntity<Any> {
        val name = request["name"].asText()
        val email = request["email"].asText()
        val password = request["hashedPassword"].asText()

        if (userRepository.findAll().any { it.email == email }) {
            return ResponseEntity.status(409).body("Email already exists.")
        }

        val newUser = User(
            name = name,
            email = email,
            hashedPassword = passwordEncoder.encode(password),
            role = wn.backend.models.Role.USER,
            wallet = "0x" + UUID.randomUUID().toString().replace("-", "").take(40)
        )

        val savedUser = userRepository.save(newUser)
        return ResponseEntity.status(201).body(savedUser)
    }

}
