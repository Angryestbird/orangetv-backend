package com.orangetv.server

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@SpringBootApplication
@EnableTransactionManagement
@EnableAuthorizationServer
@EnableResourceServer
@EnableZuulProxy
class Application {

    @Bean
    fun tokenStore() = InMemoryTokenStore()

    @Bean
    fun httpTraceRepository() = InMemoryHttpTraceRepository()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
@EnableWebSecurity
@Order(-20)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    override fun configure(http: HttpSecurity) {
        http
                .formLogin().permitAll()
                .and()
                .authorizeRequests()
                .requestMatchers(
                        AntPathRequestMatcher("/user", "POST")
                ).permitAll()
                .antMatchers("/resource**/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .ignoringRequestMatchers(
                        AntPathRequestMatcher("/user", "POST")
                )
                .and()
                .requestMatchers().antMatchers(
                        "/login",
                        "/oauth/authorize",
                        "/oauth/confirm_access",
                        "/resource**/**",
                        "/user"
                )
    }
}

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        proxyTargetClass = true
)
class MethodSecurityConfig : GlobalMethodSecurityConfiguration() {

    override fun createExpressionHandler(): MethodSecurityExpressionHandler {
        return OAuth2MethodSecurityExpressionHandler()
    }
}

@Configuration
class BaseClientDetailsConfiguration protected constructor(
        private val client: OAuth2ClientProperties
) {
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
