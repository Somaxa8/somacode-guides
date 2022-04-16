package com.somacode.guides.service

import com.somacode.guides.entity.Game
import com.somacode.guides.entity.model.GameRequest
import com.somacode.guides.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GameService {

    @Autowired lateinit var gameRepository: GameRepository
    @Autowired lateinit var documentService: DocumentService

    fun create(gameRequest: GameRequest) {
        var game = Game(
                title = gameRequest.title,
                source = gameRequest.source,
                developer = gameRequest.developer,
                logo =
        )
    }
}