package com.somacode.guides.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class FeatureType(
        @Id @GeneratedValue
        var id: Long? = null,
        var key: String? = null,
        var value: String? = null,
        var meta: String? = null,
        var url: String? = null,
        @ManyToOne
        var icon: Resource? = null,
        @Enumerated(EnumType.STRING)
        var type: Type? = null,
        @JsonIgnore
        @ManyToOne
        var feature: Feature? = null,
) {
    enum class Type { INTEGER, FLOAT, STRING, BOOLEAN, VALUES, URL, ICON }
}