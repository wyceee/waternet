package wn.backend

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class APIConfig {
    var securedPaths: Set<String> = setOf("/secured", "/api/users", "/api/measures", "/dashboard", "/supervisor", "/wallet")

    @Value("\${jwt.pass-phrase:This is very secret information}")
    val passPhrase: String? = null

    @Value("\${jwt.issuer:private-company}")
    val issuer: String? = null

    @Value("\${jwt.duration-of-validity:1200}")
    val tokenDurationOfValidity: Int = 0
}