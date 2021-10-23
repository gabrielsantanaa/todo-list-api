package com.garielsantana.projects.todolistapi.security

import com.garielsantana.projects.todolistapi.exception.BadObjectFormation
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.garielsantana.projects.todolistapi.models.dto.CredentialsDTO
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
): UsernamePasswordAuthenticationFilter() {

    init {
        this.authenticationManager = authenticationManager
    }

    @Throws(RuntimeException::class, MismatchedInputException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication? {
        return try {
            val user: CredentialsDTO = ObjectMapper().readValue(request.inputStream, CredentialsDTO::class.java)
            val auth = UsernamePasswordAuthenticationToken(user.email, user.password, arrayListOf())
            authenticationManager.authenticate(auth)
        } catch (e: UnrecognizedPropertyException) {
            throw BadObjectFormation("Invalid field: ${e.propertyName}")
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val username: String = (authResult.principal as UserDetailsImpl).username
        val token: String = jwtUtil.generateToken(username)
        response.addHeader("Authorization", "Bearer $token")
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        failed: AuthenticationException?
    ) {
        response.status = 401
        response.contentType = "application/json"
        response.writer.append(json())
    }

    private fun json(): String {
        val date = Date().time
        return ("{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Incorrect email or password\", "
                + "\"path\": \"/login\"}")
    }
}