package com.somacode.guides.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Section(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,
        @JsonIgnore
        @ManyToOne
        var game: Game? = null,
        @OneToMany(mappedBy = "section")
        var blocks: MutableList<Block> = mutableListOf()
)