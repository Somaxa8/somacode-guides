package com.somacode.guides.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired lateinit var customUserDetailsService: CustomUserDetailsService
    @Value("\${spring.application.name}") lateinit var appName: String

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : BCryptPasswordEncoder() {
            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                return super.matches(rawPassword, encodedPassword) // add master password here
            }
        }
    }

    @Autowired
    fun globalConfig(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService<UserDetailsService>(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http
                .httpBasic().realmName(appName)
                .and().sessionManagement()
                .sessionFixation().none()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().requestMatchers().antMatchers("/oauth/authorize")
                .and().authorizeRequests().antMatchers("/oauth/authorize").authenticated()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun securityEvaluationContextExtension(): SecurityEvaluationContextExtension? {
        return SecurityEvaluationContextExtension()
    }

}