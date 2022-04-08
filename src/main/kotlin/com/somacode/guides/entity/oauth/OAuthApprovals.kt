package com.somacode.guides.entity.oauth

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "oauth_approvals")
class OAuthApprovals(
        @Id @GeneratedValue
        var id: Long? = null,
        val userId: String? = null,
        val clientId: String? = null,
        val scope: String? = null,
        val status: String? = null,
        val expiresAt: Timestamp? = null,
        val lastModifiedAt: Timestamp? = null
)