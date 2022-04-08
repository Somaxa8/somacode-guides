package com.somacode.guides.entity.oauth

import javax.persistence.*

@Entity
@Table(name = "oauth_refresh_token")
class OAuthRefreshToken(
        @Id @GeneratedValue
        var id: Long? = null,
        @Column(name = "token_id")
        var tokenId: String? = null,
        @Lob
        var token: String? = null,
        @Lob
        var authentication: String? = null
)