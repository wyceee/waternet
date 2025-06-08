package wn.backend.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import wn.backend.APIConfig
import wn.backend.utils.JWToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JWTRequestFilter : OncePerRequestFilter() {
    @Autowired
    private val apiConfig: APIConfig? = null

    private val excludedPaths = listOf(
        "/authentication/login",
        "/authentication/register" // Add more public endpoints here if needed
    )

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val servletPath = request.servletPath

        // Skip filter for OPTIONS requests or excluded public paths
        if ("OPTIONS".equals(request.method, ignoreCase = true) ||
            excludedPaths.any { servletPath.startsWith(it) }
        ) {
            chain.doFilter(request, response)
            return
        }

        // If you have configured securedPaths, only enforce token on those paths
        val securedPaths = apiConfig?.securedPaths
        if (securedPaths != null && securedPaths.isNotEmpty()) {
            val isSecured = securedPaths.any { servletPath.startsWith(it) }
            if (!isSecured) {
                // Path not secured, skip token check
                chain.doFilter(request, response)
                return
            }
        }

        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to logon first.")
            return
        }

        val tokenString = authHeader.substringAfter("Bearer ").trim()
        try {
            val passPhrase = apiConfig?.passPhrase ?: throw IllegalStateException("Passphrase is not configured")
            val jwToken = JWToken.decode(tokenString, passPhrase)
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken)
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: ${e.message}")
            return
        }

        chain.doFilter(request, response)
    }
}
