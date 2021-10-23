package com.garielsantana.projects.todolistapi.controllers

import com.garielsantana.projects.todolistapi.models.User
import com.garielsantana.projects.todolistapi.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/current_user")
    fun getCurrentUser(): ResponseEntity<User> = ResponseEntity.ok(userService.getCurrentUser())

    @GetMapping("/refresh_token")
    fun refreshToken(
        response: HttpServletResponse
    ): ResponseEntity<Nothing> {
        response.addHeader("Authorization", "Bearer ${userService.refreshToken()}")
        return ResponseEntity.noContent().build()
    }

}