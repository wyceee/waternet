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
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import wn.backend.dto.RegisterResponseDTO
import wn.backend.dto.UserDTO
import java.nio.file.Paths

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

        try {
            // Generate Ethereum wallet
            val walletPassword = UUID.randomUUID().toString()
            val walletDirectory = Paths.get("wallets").toAbsolutePath().toString()
            val walletFileName =
                WalletUtils.generateNewWalletFile(walletPassword, Paths.get(walletDirectory).toFile(), false)
            val walletFilePath = "$walletDirectory/$walletFileName"

            val credentials = WalletUtils.loadCredentials(walletPassword, walletFilePath)
            val walletAddress = credentials.address
            val privateKey = credentials.ecKeyPair.privateKey.toString(16)

            // Save user with wallet details
            val newUser = User(
                name = name,
                email = email,
                hashedPassword = passwordEncoder.encode(password),
                role = wn.backend.models.Role.USER,
                wallet = walletAddress
            )

            val savedUser = userRepository.save(newUser)

            val userDTO = UserDTO(
                id = savedUser.id,
                name = savedUser.name,
                email = savedUser.email,
                wallet = savedUser.wallet
            )

            val response = RegisterResponseDTO(user = userDTO, privateKey = privateKey)
            return ResponseEntity.status(201).body(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(500).body("Failed to create wallet: ${e.message}")
        }
    }
    }
