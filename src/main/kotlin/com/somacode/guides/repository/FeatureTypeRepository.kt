package com.somacode.guides.repository

import com.somacode.guides.entity.FeatureType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeatureTypeRepository: JpaRepository<FeatureType, Long> {
}