package com.somacode.template.repository

import com.somacode.template.entity.oauth.OAuthClientDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OAuthClientDetailsRepository: JpaRepository<OAuthClientDetails, String> {

}