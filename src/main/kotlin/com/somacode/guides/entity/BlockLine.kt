package com.somacode.guides.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class BlockLine(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,
        var post: String? = null,
        @ManyToOne
        var cover: Resource? = null,
        @JsonIgnore
        @ManyToOne
        var blockCategory: BlockCategory? = null,
        @OneToMany(mappedBy = "blockLine")
        var features: MutableList<Feature> = mutableListOf()

)