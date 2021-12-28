package com.garielsantana.projects.todolistapi.repositories

import com.garielsantana.projects.todolistapi.models.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}