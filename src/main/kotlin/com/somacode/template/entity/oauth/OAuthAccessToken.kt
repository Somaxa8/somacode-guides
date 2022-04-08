package com.somacode.template.entity.oauth

import com.somacode.template.entity.User
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "oauth_access_token")
class OAuthAccessToken(
        @Column(name = "token_id")
        var tokenId: String? = null,
        @Lob
        var token: String? = null,
        @Id @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        @Column(name = "authentication_id", nullable = false)
        var authenticationId: String? = null,
        @OneToOne
        @JoinColumn(name = "user_name", referencedColumnName = "email")
        var userName: User? = null,
        @Column(name = "client_id")
        var clientId: String? = null,
        @Lob
        var authentication: String? = null,
        @Column(name = "refresh_token")
        var refreshToken: String? = null
)