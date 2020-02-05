package com.orangetv.server.service

import com.orangetv.server.domin.UserDto
import com.orangetv.server.mapper.AuthorityMapper
import com.orangetv.server.mapper.UserMapper
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * a [UserDetailsService] use MyBatis to store user credential
 */
@Service
class OrangeTvUserService(
        val userMapper: UserMapper,
        val authorityMapper: AuthorityMapper
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {

        val user = userMapper.findByEmail(email)
        val authorities = user?.let { authorityMapper.findByUser(it.id) }
                ?: throw UsernameNotFoundException(email)
        if (authorities.isEmpty()) {
            throw UsernameNotFoundException(email)
        }

        return UserDto(
                user.name,
                user.email,
                user.password,
                authorities.map {
                    SimpleGrantedAuthority(it.role)
                },
                user.motto
        )
    }
}