package com.orangetv.server.controller

import com.orangetv.server.domin.UserDto
import com.orangetv.server.entity.Authority
import com.orangetv.server.entity.User
import com.orangetv.server.mapper.AuthorityMapper
import com.orangetv.server.mapper.UserMapper
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
        val userMapper: UserMapper,
        val authorityMapper: AuthorityMapper,
        val passwordEncoder: PasswordEncoder,
        val tokenServices: ConsumerTokenServices,
        val tokenStore: TokenStore
) {

    @GetMapping("/info")
    fun getUserInfo(user: Principal): Principal {
        return user
    }

    @Transactional
    @DeleteMapping("/info")
    fun removeUser(
            @Valid @RequestBody user: User,
            @AuthenticationPrincipal userDto: UserDto,
            principal: Principal
    ): UserDto {

        if (passwordEncoder.encode(user.password) != userDto.password) {
            throw AccessDeniedException("user password not match")
        }
        val authentication = principal as OAuth2Authentication
        val accessToken = tokenStore.getAccessToken(authentication)
        tokenServices.revokeToken(accessToken.value)

        val userId = userMapper.findByEmail(userDto.email)!!.id
        authorityMapper.removeByUser(userId)
        userMapper.delete(userId)
        return userDto
    }

    @PostMapping
    @Transactional
    fun addUser(@Valid @RequestBody user: User): UserDto {
        val encodedUser = user.run {
            copy(password = passwordEncoder.encode(this.password))
        }
        userMapper.insert(encodedUser)
        authorityMapper.insert(Authority(encodedUser.id, ROLE_USER))
        return UserDto.fromUser(user, setOf(SimpleGrantedAuthority(ROLE_USER)))
    }

    companion object {
        const val ROLE_USER = "ROLE_USER"
    }
}