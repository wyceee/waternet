package wn.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import wn.backend.filters.JWTRequestFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity, jwtRequestFilter: JWTRequestFilter): SecurityFilterChain {
        println("SecurityConfig.filterChain() is called")

        http
            .csrf { it.disable() }
            .cors { }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/authentication/**", "/h2-console/**").permitAll()
                    .requestMatchers("/api/users/**").hasRole("SUPERVISOR")
//                    .requestMatchers("/api/users/**").hasAnyRole("SUPERVISOR", "USER")
            }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .headers { headers -> headers.frameOptions { it.disable() } }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun jwtRequestFilter(apiConfig: APIConfig): JWTRequestFilter = JWTRequestFilter(apiConfig)
}