package wn.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        println("SecurityConfig.filterChain() is called")
        http
            .csrf { it.disable() }
            .cors { }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/authentication/login").permitAll() // public login endpoint
                    .anyRequest().authenticated() // all others need auth
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }

        return http.build()
    }
}
