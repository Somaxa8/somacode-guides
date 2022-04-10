package com.somacode.guides.repository

import com.somacode.guides.entity.Resource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceRepository: JpaRepository<Resource, Long> {
}