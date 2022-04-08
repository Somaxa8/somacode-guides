package com.somacode.template.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class PropertyConfig {

    companion object {
        lateinit var BASE_URL: String
        lateinit var PROTOCOL: String
    }

    @Value("\${custom.base-url}") lateinit var baseUrl: String
    @Value("\${custom.protocol}") lateinit var protocol: String

    @PostConstruct
    fun init() {
        BASE_URL = baseUrl
        PROTOCOL = protocol
    }

}