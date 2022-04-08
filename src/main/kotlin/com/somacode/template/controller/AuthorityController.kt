package com.somacode.template.controller

import com.somacode.template.entity.Authority
import com.somacode.template.service.AuthorityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorityController {

    @Autowired lateinit var authorityService: AuthorityService


    @GetMapping("/api/authorities")
    fun getAuthorities(): ResponseEntity<List<Authority>> {
        return ResponseEntity.status(HttpStatus.OK).body(authorityService.findAll())
    }

    @GetMapping("/api/users/{userId}/authorities")
    fun getAuthoritiesByUser(@PathVariable userId: Long): ResponseEntity<List<Authority>> {
        return ResponseEntity.status(HttpStatus.OK).body(authorityService.findByUserId(userId))
    }

    @PatchMapping("/api/authorities/{role}/users/{userId}/relate")
    fun patchAuthorityRelateUser(
            @PathVariable role: Authority.Role,
            @PathVariable userId: Long
    ): ResponseEntity<Void> {
        authorityService.relateUser(role, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @PatchMapping("/api/authorities/{role}/users/{userId}/unrelate")
    fun patchAuthorityUnrelateUser(
            @PathVariable role: Authority.Role,
            @PathVariable userId: Long
    ): ResponseEntity<Void> {
        authorityService.unrelateUser(role, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

}