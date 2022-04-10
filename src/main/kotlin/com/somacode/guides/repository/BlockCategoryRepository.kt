package com.somacode.guides.repository

import com.somacode.guides.entity.BlockCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockCategoryRepository: JpaRepository<BlockCategory, Long> {
}