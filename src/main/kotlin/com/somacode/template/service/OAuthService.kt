package com.somacode.template.service

import com.somacode.template.repository.OAuthAccessTokenRepository
import com.somacode.template.security.SecurityTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.web.HttpRequestMethodNotSupportedException
import java.security.Principal
import javax.transaction.Transactional

@Service
@Transactional
class OAuthService {

    @Value("\${custom.my-secret-token}") lateinit var secretToken: String
    @Value("\${spring.application.name}") lateinit var clientId: String
    @Autowired lateinit var tokenEndpoint: TokenEndpoint
    @Autowired lateinit var securityTool: SecurityTool
    @Autowired lateinit var oAuthAccessTokenRepository: OAuthAccessTokenRepository
    @Autowired lateinit var userService: UserService


    @Throws(HttpRequestMethodNotSupportedException::class)
    fun login(email: String, password: String): OAuth2AccessToken {
        if (!userService.existsByEmailAndActiveTrue(email)) {
            throw InvalidGrantException("Bad credentials")
        }
        val accessToken: ResponseEntity<OAuth2AccessToken> = loginByEmailAndPassword(email, password)
        return accessToken.body!!
    }

    fun logout() {
        logoutByUserId(securityTool.getUserId())
    }

    @Throws(HttpRequestMethodNotSupportedException::class)
    private fun loginByEmailAndPassword(email: String?, password: String?): ResponseEntity<OAuth2AccessToken> {
        val parameters: MutableMap<String, String?> = HashMap()
        parameters["username"] = email
        parameters["password"] = password
        parameters["grant_type"] = "password"
        parameters["scope"] = "read write"
        parameters["client_secret"] = secretToken
        parameters["client_id"] = clientId
        val p: Any = User(clientId, secretToken, SecurityTool.getAllAuthorities())
        val principal: Principal = UsernamePasswordAuthenticationToken(p, secretToken, SecurityTool.getAllAuthorities())
        return tokenEndpoint.postAccessToken(principal, parameters)
    }

    @Throws(HttpRequestMethodNotSupportedException::class)
    private fun customRefresh(refreshToken: String): ResponseEntity<OAuth2AccessToken> {
        val parameters: MutableMap<String, String?> = HashMap()
        parameters["refresh_token"] = refreshToken
        parameters["grant_type"] = "refresh_token"
        parameters["client_secret"] = secretToken
        parameters["client_id"] = clientId

        val p: Any = User(clientId, secretToken, SecurityTool.getAllAuthorities())
        val principal: Principal = UsernamePasswordAuthenticationToken(p, secretToken, SecurityTool.getAllAuthorities())
        return tokenEndpoint.postAccessToken(principal, parameters)
    }

    @Throws(HttpRequestMethodNotSupportedException::class)
    fun refresh(refreshToken: String): OAuth2AccessToken {
        val accessToken: ResponseEntity<OAuth2AccessToken> = customRefresh(refreshToken)
        return accessToken.body!!
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
    fun logoutByUserId(userId: Long) {
        oAuthAccessTokenRepository.deleteByUserName_Id(userId)
    }

}