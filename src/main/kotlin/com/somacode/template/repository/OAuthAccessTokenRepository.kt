package com.somacode.template.repository

import com.somacode.template.entity.oauth.OAuthAccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OAuthAccessTokenRepository: JpaRepository<OAuthAccessToken, String> {

    fun deleteByUserName_Id(userId: Long)

}