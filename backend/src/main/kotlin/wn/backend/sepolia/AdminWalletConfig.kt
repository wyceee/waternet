package wn.backend.sepolia

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.crypto.Credentials

@Configuration
class AdminWalletConfig {
    @Bean
    fun adminCredentials(): Credentials {
        val dotenv = Dotenv.load()
        val privateKey = dotenv["ADMIN_PRIVATE_KEY"]
        require(!privateKey.isNullOrBlank()) { "ADMIN_PRIVATE_KEY must be set!" }
        return Credentials.create(privateKey)
    }
}