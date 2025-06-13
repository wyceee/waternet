package wn.backend.sepolia

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class Web3jConfig {
    @Bean
    fun web3j(): Web3j {
        val dotenv = Dotenv.load()
        val rpcUrl = dotenv["WEB3J_RPC_URL"]
        return Web3j.build(HttpService(rpcUrl))
    }
}