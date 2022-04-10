package com.somacode.guides.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Block(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,
        var post: String? = null,
        @ManyToOne
        var icon: Resource? = null,
        @ManyToOne
        var cover: Resource? = null,
        @JsonIgnore
        @ManyToOne
        var section: Section? = null,
        @OneToMany(mappedBy = "block")
        var blockCategories: MutableList<BlockCategory> = mutableListOf()
)