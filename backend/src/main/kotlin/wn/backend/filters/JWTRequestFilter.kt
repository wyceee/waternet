package wn.backend.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import wn.backend.APIConfig
import wn.backend.utils.JWToken

class JWTRequestFilter(
    private val apiConfig: APIConfig) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            chain.doFilter(request, response)
            return
        }

        val securedPaths = apiConfig.securedPaths
        val isSecured = securedPaths.any { path ->
            request.servletPath == path || request.servletPath.startsWith("$path/")
        }

        if (!isSecured) {
            chain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided.")
            return
        }

        try {
            val tokenString = authHeader.substringAfter("Bearer ").trim()
            val jwToken = JWToken.decode(tokenString, apiConfig.passPhrase ?: "")
            val authorities = listOf(SimpleGrantedAuthority("ROLE_${jwToken.role.name}"))
            val authentication = UsernamePasswordAuthenticationToken(jwToken.callName, null, authorities)
            SecurityContextHolder.getContext().authentication = authentication

            chain.doFilter(request, response)
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.")
        }
    }
}