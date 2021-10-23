package com.garielsantana.projects.todolistapi.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.garielsantana.projects.todolistapi.exception.AuthorizationException
import com.garielsantana.projects.todolistapi.exception.BadObjectFormation
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandlerFilter(
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver
): OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: AuthorizationException) {
            resolver.resolveException(request, response, null, e)
        } catch (e: BadObjectFormation) {
            resolver.resolveException(request, response, null, e)
        }
    }

}