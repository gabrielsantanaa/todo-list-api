package com.garielsantana.projects.todolistapi.services

import com.garielsantana.projects.todolistapi.exception.ObjectNotFoundException
import com.garielsantana.projects.todolistapi.models.Todo
import com.garielsantana.projects.todolistapi.models.User
import com.garielsantana.projects.todolistapi.repositories.UserRepository
import com.garielsantana.projects.todolistapi.security.JWTUtil
import com.garielsantana.projects.todolistapi.utils.getAuthenticatedUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil
) {

    fun getCurrentUser(): User = userRepository
        .findByEmail(getAuthenticatedUser().username) ?: throw ObjectNotFoundException("This user does not exist")

    fun refreshToken(): String = jwtUtil.generateToken(getCurrentUser().email)

}