package com.garielsantana.projects.todolistapi.utils

import com.garielsantana.projects.todolistapi.security.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

fun getAuthenticatedUser(): UserDetails {
    try {
        return SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
    } catch (e: Exception) {
        throw RuntimeException("Unknown error. Try again later.")
    }
}
