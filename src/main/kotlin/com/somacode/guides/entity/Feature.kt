package com.somacode.guides.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Feature(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @Enumerated(EnumType.STRING)
        var display: Display? = null,
        @JsonIgnore
        @ManyToOne
        var blockLine: BlockLine? = null,
        @JsonIgnore
        @ManyToOne
        var block: Block? = null,
        @OneToMany(mappedBy = "feature")
        var featureTypes: MutableList<FeatureType> = mutableListOf()
) {
        enum class Display { COLUMN, ROW, TABLE }
}