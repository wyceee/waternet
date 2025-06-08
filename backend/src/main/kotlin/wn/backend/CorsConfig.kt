package wn.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("http://localhost:*")
        config.addAllowedHeader(HttpHeaders.AUTHORIZATION)
        config.addAllowedHeader(HttpHeaders.CONTENT_TYPE)
        config.addExposedHeader(HttpHeaders.AUTHORIZATION)
        config.addExposedHeader(HttpHeaders.CONTENT_TYPE)
        config.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}