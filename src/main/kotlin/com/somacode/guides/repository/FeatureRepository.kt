package com.somacode.guides.repository

import com.somacode.guides.entity.Feature
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeatureRepository: JpaRepository<Feature, Long> {
}