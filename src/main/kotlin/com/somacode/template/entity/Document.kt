package com.somacode.template.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.somacode.template.service.tool.StorageSerializer
import javax.persistence.*

@Entity
class Document(
        @Id @GeneratedValue
        var id: Long? = null,
        var name: String? = null,
        var baseName: String? = null,
        var extension: String? = null,
        var description: String? = null,
        var tag: String? = null,
        @Enumerated(EnumType.STRING)
        var type: Type? = null,
        @JsonSerialize(using = StorageSerializer::class)
        var url: String? = null
) {

    enum class Type { IMAGE, DOCUMENT }

}