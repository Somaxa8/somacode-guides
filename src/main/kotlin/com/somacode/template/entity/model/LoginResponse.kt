package com.somacode.template.entity.model

import com.somacode.template.entity.Authority
import com.somacode.template.entity.User
import org.springframework.security.oauth2.common.OAuth2AccessToken

data class LoginResponse(
        var oAuth2AccessToken: OAuth2AccessToken,
        var user: User? = null,
        var authorities: Set<Authority>
)