package com.garielsantana.projects.todolistapi.services

import com.garielsantana.projects.todolistapi.exception.ObjectNotFoundException
import com.garielsantana.projects.todolistapi.models.User
import com.garielsantana.projects.todolistapi.repositories.UserRepository
import com.garielsantana.projects.todolistapi.security.JWTUtil
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil
) {

    fun getCurrentUser(): User = userRepository
        .findByEmail(com.garielsantana.projects.todolistapi.utils.getAuthenticatedUser().username) ?: throw ObjectNotFoundException("This user does not exist")

    fun refreshToken(): String = jwtUtil.generateToken(getCurrentUser().email)

}