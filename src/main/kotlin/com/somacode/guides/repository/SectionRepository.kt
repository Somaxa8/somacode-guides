package com.somacode.guides.repository

import com.somacode.guides.entity.Section
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SectionRepository: JpaRepository<Section, Long> {
}