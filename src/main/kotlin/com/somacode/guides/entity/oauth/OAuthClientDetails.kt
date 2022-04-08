package com.somacode.guides.entity.oauth

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "oauth_client_details")
class OAuthClientDetails(
        @Id @Column(name = "client_id", nullable = false)
        var clientId: String? = null,
        @Column(name = "resource_ids")
        var resourceId: String? = null,
        @Column(name = "client_secret")
        var clientSecret: String? = null,
        var scope: String? = null,
        @Column(name = "authorized_grant_types")
        var authorizedGrantTypes: String? = null,
        @Column(name = "web_server_redirect_uri")
        var webServerRedirectUri: String? = null,
        var authorities: String? = null,
        @Column(name = "access_token_validity")
        var accessTokenValidity: Int? = null,
        @Column(name = "refresh_token_validity")
        var refreshTokenValidity: Int? = null,
        @Column(name = "additional_information")
        var additionalInformation: String? = null,
        var autoapprove: String? = null
)