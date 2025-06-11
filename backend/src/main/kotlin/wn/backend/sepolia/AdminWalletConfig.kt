package wn.backend.sepolia

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.crypto.Credentials

@Configuration
class AdminWalletConfig {
    @Bean
    fun adminCredentials(): Credentials {
        val privateKey = System.getenv("ADMIN_PRIVATE_KEY") // Or from application.properties
        require(!privateKey.isNullOrBlank()) { "ADMIN_PRIVATE_KEY must be set!" }
        return Credentials.create(privateKey)
    }
}