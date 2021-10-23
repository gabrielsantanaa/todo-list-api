package com.garielsantana.projects.todolistapi.security

import com.garielsantana.projects.todolistapi.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String?): UserDetails {
        email?.let {
            val user = userRepository.findByEmail(email)
            user?.let {
                return UserDetailsImpl(user.id, user.email, user.password, user.getAuthorities())
            }
        }
        throw UsernameNotFoundException("Email not found: $email")
    }
}