package com.somacode.guides.repository

import com.somacode.guides.entity.BlockLine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockLineRepository: JpaRepository<BlockLine, Long> {
}