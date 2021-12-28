package com.garielsantana.projects.todolistapi.controllers.advices

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.garielsantana.projects.todolistapi.exception.AuthorizationException
import com.garielsantana.projects.todolistapi.exception.BadObjectFormation
import com.garielsantana.projects.todolistapi.exception.ObjectNotFoundException
import com.garielsantana.projects.todolistapi.models.StandardError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class MainControllerAdvice {

    @ExceptionHandler(AuthorizationException::class)
    fun authorizationException(
        exception: AuthorizationException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        val standardError = StandardError(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = exception.message!!,
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardError)
    }

    @ExceptionHandler(ObjectNotFoundException::class)
    fun objectNotFoundException(
        exception: ObjectNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        val standardError = StandardError(
            status = HttpStatus.NOT_FOUND.value(),
            error = exception.message!!,
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError)
    }

    @ExceptionHandler(BadObjectFormation::class)
    fun badObjectFormation(
        exception: BadObjectFormation,
        request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        val standardError = StandardError(
            status = HttpStatus.BAD_REQUEST.value(),
            error = exception.message!!,
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(
        exception: RuntimeException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        val standardError = StandardError(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = exception.message ?: "Internal error",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError)
    }

}