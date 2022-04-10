package com.somacode.guides.repository

import com.somacode.guides.entity.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository: JpaRepository<Game, Long> {
}