package com.somacode.template.entity.oauth

import java.sql.Blob
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