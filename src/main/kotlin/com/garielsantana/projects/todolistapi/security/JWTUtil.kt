package com.garielsantana.projects.todolistapi.security

import com.garielsantana.projects.todolistapi.exception.AuthorizationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {
    @Value("\${jwt.secret}")
    private val secretKey: String? = null

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    fun generateToken(username: String): String {
        return Jwts.builder().setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + expiration!!))
            .signWith(SignatureAlgorithm.HS512, secretKey!!.toByteArray())
            .compact()
    }

    //if the token has expired, an exception is thrown
    fun getClaims(token: String): Claims {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey!!.toByteArray())
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
           throw AuthorizationException("The token is expired")
        }
    }

}