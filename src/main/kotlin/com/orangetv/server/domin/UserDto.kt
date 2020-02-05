package com.orangetv.server.domin

import com.orangetv.server.entity.User
import org.springframework.security.core.GrantedAuthority

data class UserDto(
        val name: String,
        val email: String,
        @get:JvmName("password") val password: String,
        @get:JvmName("authorities") val authorities: Collection<GrantedAuthority>,
        val motto: String
) : org.springframework.security.core.userdetails.User(email, password, authorities) {

    val user by lazy<com.orangetv.server.entity.User> {

        User(
                name = name,
                email = email,
                password = password,
                motto = motto,
                enabled = true
        )
    }

    companion object {
        fun fromUser(user: User, authorities: Collection<GrantedAuthority>): UserDto {
            return UserDto(user.name, user.email, user.password, authorities, user.motto)
        }
    }
}