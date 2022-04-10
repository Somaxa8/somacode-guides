package com.somacode.guides.entity

import javax.persistence.*

@Entity
class Game(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var source: String? = null,
        var names: String? = null,
        var developer: String? = null,
        var distributor: String? = null,
        var lauching: String? = null,
        @ManyToOne
        var logo: Resource? = null,
        @OneToMany(mappedBy = "game")
        var sections: MutableList<Section> = mutableListOf()
)