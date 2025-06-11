package wn.backend.sepolia

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class Web3jConfig {
    @Bean
    fun web3j(): Web3j {
        val rpcUrl = System.getenv("WEB3J_RPC_URL") ?: "https://sepolia.infura.io/v3/YOUR_INFURA_KEY"
        return Web3j.build(HttpService(rpcUrl))
    }
}