package com.somacode.guides.entity.model

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

data class GameRequest(
        var title: String,
        var source: String,
        var developer: String,
        var logoFile: MultipartFile,
        var launching: LocalDate?,
        var distributor: String?,
        var names: String?
)