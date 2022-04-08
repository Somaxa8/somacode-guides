package com.somacode.guides.security

import com.google.gson.JsonObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogoutSuccess: AbstractAuthenticationTargetUrlRequestHandler(), LogoutSuccessHandler {

    @Autowired lateinit var tokenStore: TokenStore


    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
//        LogService.out.debug("/logout")
        val token = request.getHeader("Authorization")
        if (token != null && token.startsWith("Bearer ")) {
            val tokenString = token.substring(7)
            val oAuth2AccessToken = tokenStore.readAccessToken(tokenString)
            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessToken(oAuth2AccessToken)
                setSuccessBody(response, tokenString)
            } else {
                setUnauthorizedBody(response, tokenString)
            }
        } else {
            setUnauthorizedBody(response)
        }
    }

    private fun setSuccessBody(response: HttpServletResponse, tokenString: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("message", "token_revoked")
        jsonObject.addProperty("description", "Access token revoked: $tokenString")
        response.status = HttpServletResponse.SC_OK
        response.contentType = "application/json;charset=UTF-8"
        response.writer.print(jsonObject)
    }

    private fun setUnauthorizedBody(response: HttpServletResponse, tokenString: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("error", "invalid_token")
        jsonObject.addProperty("error_description", "Invalid access token: $tokenString")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"
        response.writer.print(jsonObject)
    }

    private fun setUnauthorizedBody(response: HttpServletResponse) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("timestamp", ZonedDateTime.now().toString())
        jsonObject.addProperty("status", 401)
        jsonObject.addProperty("error", "Unauthorized")
        jsonObject.addProperty("message", "Full authentication is required to access this resource")
        jsonObject.addProperty("path", "/api/logout")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"
        response.writer.print(jsonObject)
    }

}