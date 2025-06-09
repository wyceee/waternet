package wn.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
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
                    .requestMatchers("/authentication/login", "/h2-console/**").permitAll()
                    .anyRequest().authenticated()
            }
            .headers { it.frameOptions().disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }

        return http.build()
    }
}