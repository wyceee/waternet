package wn.backend.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Claims
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import wn.backend.models.Role
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

class JWToken(
    val callName: String, val userId: Long, val role: Role
) {
    var ipAddress: String? = null

    fun encode(issuer: String?, passphrase: String, expirationSec: Int): String {
        val key = getKey(passphrase)
        return Jwts.builder()
            .claim(JWT_CALLNAME_CLAIM, this.callName)
            .claim(JWT_USERID_CLAIM, this.userId)
            .claim(JWT_ROLE_CLAIM, this.role.name)
            .claim(JWT_IPADDRESS_CLAIM, this.ipAddress)
            .setIssuer(issuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationSec * 1000L))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    companion object {
        const val JWT_ATTRIBUTE_NAME: String = "JWToken"
        private const val JWT_CALLNAME_CLAIM = "sub"
        private const val JWT_USERID_CLAIM = "id"
        private const val JWT_ROLE_CLAIM = "role"
        private const val JWT_IPADDRESS_CLAIM = "ip"

        fun decode(token: String?, passphrase: String): JWToken {
            val key = getKey(passphrase)
            val jws: Jws<Claims> = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            val claims: Claims = jws.body

            val decoded = JWToken(
                claims[JWT_CALLNAME_CLAIM].toString(),
                claims[JWT_USERID_CLAIM].toString().toLong(),
                Role.valueOf(claims[JWT_ROLE_CLAIM].toString())
            )
            decoded.ipAddress = claims[JWT_IPADDRESS_CLAIM]?.toString()
            return decoded
        }

        private fun getKey(passphrase: String): Key {
            val hmacKey = passphrase.toByteArray(StandardCharsets.UTF_8)
            return Keys.hmacShaKeyFor(hmacKey)
        }
    }
}