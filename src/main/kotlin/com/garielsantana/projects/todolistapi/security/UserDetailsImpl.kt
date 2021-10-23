package com.garielsantana.projects.todolistapi.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val id: Long,
    private val email: String,
    private val password: String,
    profileAuthorities: Set<ProfileAuthorities>
): UserDetails {

    private val profileAuthorities: Collection<GrantedAuthority>

    init {
        this.profileAuthorities = profileAuthorities.map { SimpleGrantedAuthority(it.description) }.toList()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> = profileAuthorities

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}