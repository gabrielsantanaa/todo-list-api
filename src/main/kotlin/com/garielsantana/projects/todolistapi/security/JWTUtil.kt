package com.garielsantana.projects.todolistapi.security

import com.garielsantana.projects.todolistapi.exception.AuthorizationException
import com.garielsantana.projects.todolistapi.utils.JWT_EXPIRATION_TIME
import com.garielsantana.projects.todolistapi.utils.JWT_SECRET_KEY
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {

    fun generateToken(username: String): String {
        return Jwts.builder().setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY.toByteArray())
            .compact()
    }


    fun getClaims(token: String): Claims {
        return try {
            Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY.toByteArray())
                .parseClaimsJws(token)
                .body
        }
        //if the token has expired, this exception is thrown
        catch (e: ExpiredJwtException) {
           throw AuthorizationException("The token is expired")
        }
    }

}