package com.somacode.guides.service.tool

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

object CriteriaTool {


    fun <T> criteria(entityManager: EntityManager, clazz: Class<T>): Root<T> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(clazz)
        return q.from(clazz)
    }

    fun <T> page(entityManager: EntityManager, q: CriteriaQuery<T>, page: Int, size: Int): PageImpl<T> {
        val query = entityManager.createQuery(q)
        val total = query.resultList.size
        return PageImpl(query.setFirstResult(page * size).setMaxResults(size).resultList, PageRequest.of(page, size), total.toLong())
    }

}