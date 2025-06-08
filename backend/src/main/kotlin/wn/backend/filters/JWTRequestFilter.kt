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

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val servletPath = request.servletPath

        if ("OPTIONS".equals(request.method, ignoreCase = true) ||
            apiConfig?.securedPaths?.none { servletPath.startsWith(it) } == true
        ) {
            chain.doFilter(request, response)
            return
        }

        val encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to logon first.")
            return
        }

        val jwToken: JWToken
        try {
            val passPhrase = apiConfig?.passPhrase ?: throw IllegalStateException("Passphrase is not configured")
            jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""), passPhrase)
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: " + e.message)
            return
        }

        request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken)
        chain.doFilter(request, response)
    }
}