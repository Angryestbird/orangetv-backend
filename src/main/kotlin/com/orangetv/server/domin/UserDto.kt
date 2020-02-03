package com.orangetv.server.domin

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

data class UserDto(
        val email: String,
        @get:JvmName("password") val password: String,
        @get:JvmName("authorities") val authorities: Collection<GrantedAuthority>,
        val motto: String
) : User(email, password, authorities)