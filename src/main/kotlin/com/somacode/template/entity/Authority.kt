package com.somacode.template.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Authority(
        @Id @Enumerated(EnumType.STRING)
        var role: Role? = null,
        var title: String? = null,

        @JsonIgnore
        @ManyToMany
        @JoinTable(
                name = "rel_user_authority",
                joinColumns = [JoinColumn(name = "authority_role", referencedColumnName = "role")],
                inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
        )
        var users: MutableSet<User> = mutableSetOf(),

        @Transient
        var enabled: Boolean? = null
) {
        enum class Role {
                SUPER_ADMIN, ADMIN, SWAGGER, USER
        }
}