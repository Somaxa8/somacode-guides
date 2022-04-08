package com.somacode.guides.repository

import com.somacode.guides.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository: JpaRepository<Authority, Authority.Role> {

    fun findByUsers_Id(userId: Long): List<Authority>

}