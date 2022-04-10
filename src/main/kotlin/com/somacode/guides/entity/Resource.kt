package com.somacode.guides.entity

import javax.persistence.*

@Entity
class Resource(
        @Id @GeneratedValue
        var id: Long? = null,
        var value: String? = null,
        var tag: String? = null,
        @Enumerated(EnumType.STRING)
        var type: Type? = null,
        @OneToOne
        var image: Document? = null,
) {
        enum class Type { ICON, COVER, LOGO, IMAGE }
}