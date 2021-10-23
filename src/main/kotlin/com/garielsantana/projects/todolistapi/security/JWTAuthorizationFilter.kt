package com.garielsantana.projects.todolistapi.security

import com.garielsantana.projects.todolistapi.exception.AuthorizationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JWTAuthorizationFilter(
    private val jwtUtil: JWTUtil,
    private val userDetailsService: UserDetailsService,
    authenticationManager: AuthenticationManager
): BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class, AuthorizationException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        request.getHeader("Authorization")?.let { header ->
            if(header.startsWith("Bearer ")) {
                getAuthentication(header.substring(7)).let {
                    SecurityContextHolder.getContext().authentication = it
                }
            }
        } ?: run {
            throw AuthorizationException("Authorization header is missing")
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val claims = jwtUtil.getClaims(token)
        val user = userDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}