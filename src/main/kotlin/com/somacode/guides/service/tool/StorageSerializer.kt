package com.somacode.guides.service.tool

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.somacode.guides.config.PropertyConfig

class StorageSerializer: JsonSerializer<String>() {

//    companion object {
//        const val REAL_STORAGE_PATH = "${PropertyConfig.PROTOCOL}://${PropertyConfig.BASE_URL}/storage"
//    }

    override fun serialize(value: String, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
//        jsonGenerator.writeString(REAL_STORAGE_PATH + value)
        jsonGenerator.writeString("${PropertyConfig.PROTOCOL}://${PropertyConfig.BASE_URL}/storage$value")
    }

}