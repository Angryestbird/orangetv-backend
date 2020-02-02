package com.orangetv.server.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Service

@Service
class UserDetailsService : UserDetailsService {

    val userDetailsManager: InMemoryUserDetailsManager = InMemoryUserDetailsManager(
            User.withUsername("user")
                    .password("password")
                    .roles("USER")
                    .build(),
            User.withUsername("admin")
                    .password("password")
                    .roles("USER", "ADMIN")
                    .build()
    )

    override fun loadUserByUsername(username: String?): UserDetails {
        return userDetailsManager.loadUserByUsername(username)
    }
}