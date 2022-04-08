package com.somacode.guides.security

import com.somacode.guides.entity.Authority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import javax.sql.DataSource

@Configuration
class OAuth2Config {

    @Autowired lateinit var dataSource: DataSource


    @Order(1)
    @Configuration
    class FormLoginWebSecurityConfigurerAdapter: WebSecurityConfigurerAdapter() {
        @Throws(java.lang.Exception::class)
        public override fun configure(http: HttpSecurity) {
            http
                    .requestMatchers().antMatchers(
                            "/swagger-resources/**", "/swagger-ui.html**", "/swagger",
                            "/oauth/authorize",
                            "/admin/**",
                            "/login", "/logout")
//                            "/h2-console/**")
                    .and().csrf().disable()
                    .authorizeRequests()
//                    .antMatchers("/admin/**", "/h2-console/**").hasAnyAuthority(Authority.Name.ROLE_ADMIN.toString())
                    .antMatchers("/admin/**").hasAnyAuthority(Authority.Role.ADMIN.toString())
                    .antMatchers("/swagger-resources/**", "/swagger-ui.html**", "/swagger").hasAnyAuthority(Authority.Role.SWAGGER.toString(), Authority.Role.ADMIN.toString())
                    .and().formLogin().loginPage("/login").failureUrl("/login?error=1").permitAll()
                    .and().logout().logoutUrl("/logout").permitAll()
        }
    }

    @Bean
    fun tokenStore(): TokenStore? {
        return object: JdbcTokenStore(dataSource) {
            private val jdbcTemplate = JdbcTemplate(dataSource)
            private val DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = "select token_id, token from oauth_access_token where token_id = ?"
            private val selectAccessTokenSql = DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT
            override fun readAccessToken(tokenValue: String): OAuth2AccessToken? {
                var accessToken: OAuth2AccessToken? = null
                try {
                    accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, RowMapper { rs, _ -> deserializeAccessToken(rs.getBytes(2)) }, extractTokenKey(tokenValue))
                } catch (e: EmptyResultDataAccessException) {
//                    if (LOG.isInfoEnabled()) {
//                        LOG.info("Failed to find access token for token " + tokenValue);
//                    }
                } catch (e: IllegalArgumentException) {
//                    LogService.out.warn("Failed to deserialize access token for $tokenValue")
                    removeAccessToken(tokenValue)
                }
                return accessToken
            }
        }
    }

    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {
        @Autowired var tokenStore: TokenStore? = null
        @Autowired var logoutSuccess: LogoutSuccess? = null
        @Value("\${spring.application.name}") var appName: String? = null

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .exceptionHandling().authenticationEntryPoint(BasicAuthenticationEntryPoint())
                    .and().logout().logoutUrl("/api/logout").logoutSuccessHandler(logoutSuccess)
                    .and().csrf().disable().headers().frameOptions().disable()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().authorizeRequests()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/register").permitAll()
                    .antMatchers("/*api/**").authenticated()
        }

        override fun configure(resources: ResourceServerSecurityConfigurer) {
            resources.resourceId("res_$appName").tokenStore(tokenStore)
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected class AuthorizationServerConfiguration : AuthorizationServerConfigurerAdapter() {
        @Autowired var dataSource: DataSource? = null
        @Autowired var tokenStore: TokenStore? = null
        @Bean protected fun authorizationCodeServices(): AuthorizationCodeServices { return JdbcAuthorizationCodeServices(dataSource) }
        @Bean protected fun approvalStore(): ApprovalStore { return JdbcApprovalStore(dataSource) }
        @Autowired var authenticationManager: AuthenticationManager? = null

        override fun configure(security: AuthorizationServerSecurityConfigurer) {
            security.allowFormAuthenticationForClients()
        }

        @Throws(java.lang.Exception::class)
        override fun configure(clients: ClientDetailsServiceConfigurer) {
            clients.jdbc(dataSource)
        }

        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
            endpoints
                    .authorizationCodeServices(authorizationCodeServices())
                    .approvalStore(approvalStore())
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
        }
    }

}