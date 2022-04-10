package com.somacode.guides.repository

import com.somacode.guides.entity.Block
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockRepository: JpaRepository<Block, Long> {
}