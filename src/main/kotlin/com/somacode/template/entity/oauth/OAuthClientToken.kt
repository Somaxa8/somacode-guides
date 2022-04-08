package com.somacode.template.entity.oauth

import com.somacode.template.entity.User
import java.sql.Blob
import javax.persistence.*

@Entity
@Table(name = "oauth_client_token")
class OAuthClientToken(
        @Column(name = "token_id")
        var tokenId: String? = null,
        @Lob
        var token: String? = null,
        @Id
        @Column(name = "authentication_id")
        var authenticationId: String? = null,
        @OneToOne
        @JoinColumn(name = "user_name", referencedColumnName = "email")
        var userName: User? = null,
        @Column(name = "client_id")
        var clientId: String? = null
)