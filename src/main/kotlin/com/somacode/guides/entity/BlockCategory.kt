package com.somacode.guides.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class BlockCategory(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,
        @JsonIgnore
        @ManyToOne
        var block: Block? = null,
        @OneToMany(mappedBy = "blockCategory")
        var blockLines: MutableList<BlockLine> = mutableListOf()
)