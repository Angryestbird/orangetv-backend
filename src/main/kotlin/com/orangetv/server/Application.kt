package com.orangetv.server

import org.springframework.boot.actuate.trace.http.HttpTraceRepository
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import java.util.*

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@EnableZuulProxy
class Application {
    @Bean
    fun httpTraceRepository(): HttpTraceRepository {
        return InMemoryHttpTraceRepository()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
@Order(-20)
class SecurityConfig(
        val authenticationConfiguration: AuthenticationConfiguration
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    override fun configure(http: HttpSecurity) {
        http
                .formLogin().permitAll()
                .and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
                .and()
                .authorizeRequests().anyRequest().authenticated()
    }
}

@Configuration
class BaseClientDetailsConfiguration protected constructor(private val client: OAuth2ClientProperties) {
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    fun oauth2ClientDetails(): BaseClientDetails {
        val details = BaseClientDetails()
        if (client.clientId == null) {
            client.clientId = UUID.randomUUID().toString()
        }
        details.clientId = client.clientId
        details.clientSecret = client.clientSecret
        details.setAuthorizedGrantTypes(Arrays.asList("authorization_code",
                "password", "client_credentials", "implicit", "refresh_token"))
        details.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")
        details.registeredRedirectUri = setOf("http://example.com")
        return details
    }
}
