package com.orangetv.server.controller

import com.orangetv.server.domin.UserDto
import com.orangetv.server.entity.Authority
import com.orangetv.server.entity.User
import com.orangetv.server.mapper.AuthorityMapper
import com.orangetv.server.mapper.UserMapper
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.annotation.security.PermitAll
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
        val userMapper: UserMapper,
        val authorityMapper: AuthorityMapper,
        val passwordEncoder: PasswordEncoder
) {

    @GetMapping
    fun getUserInfo(user: Principal): Principal {
        return user
    }

    @DeleteMapping
    @Transactional
    fun removeUser(@RequestBody userDto: UserDto, user: Principal): UserDto {
//        val user = userDto.user
//        userDto.authorities.forEach {
//            authorityMapper.insert(Authority(user.id, it.authority))
//        }
//        userMapper.insert(user)
        return userDto
    }

    @PostMapping
    @Transactional
    @PermitAll
    fun addUser(@Valid @RequestBody user: User): UserDto {
        userMapper.insert(
                user.run {
                    copy(password = passwordEncoder.encode(this.password))
                }
        )
        authorityMapper.insert(Authority(user.id, ROLE_USER))
        return UserDto.fromUser(user, setOf(SimpleGrantedAuthority(ROLE_USER)))
    }

    companion object {
        const val ROLE_USER = "ROLE_USER"
    }
}