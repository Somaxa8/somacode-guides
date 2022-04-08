package com.somacode.template.repository.criteria

import com.somacode.template.entity.*
import com.somacode.template.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate

@Repository
class UserCriteria {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?, role: Authority.Role): Page<User> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(User::class.java)
        val user = q.from(User::class.java)

        val order: Path<Set<String>> = user.get(User_.ID)

        val predicates: MutableList<Predicate> = mutableListOf()


        q.select(user).where(
            *predicates.toTypedArray(),
            cb.isMember(Authority(role = role), user.get(User_.authorities))
        ).orderBy(cb.desc(order))

        return CriteriaTool.page(entityManager, q, page, size)
    }
}