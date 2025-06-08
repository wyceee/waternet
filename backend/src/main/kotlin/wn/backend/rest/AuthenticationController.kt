package wn.backend.rest

import com.fasterxml.jackson.databind.node.ObjectNode
import wn.backend.APIConfig
import wn.backend.models.User
import wn.backend.utils.JWToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wn.backend.models.Role
import java.util.*

@RestController
@RequestMapping("/authentication")
class AuthenticationController {
    @Autowired
    private val apiConfig: APIConfig? = null

    private val random = Random()

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: ObjectNode): ResponseEntity<User> {
        val email = loginRequest["email"].asText()
        val password = loginRequest["password"].asText()

        val expected = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        if (expected != password) {
            return ResponseEntity.status(406).build()
        }

        val user = User(random.nextLong(), expected, email, password, Role.USER)

        val token = JWToken(user.name, user.id!!, user.role)
        val tokenString = token.encode(
            apiConfig?.issuer, apiConfig?.passPhrase ?: "",
            apiConfig?.tokenDurationOfValidity ?: 0
        )

        return ResponseEntity.accepted()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $tokenString")
            .body(user)
    }
}