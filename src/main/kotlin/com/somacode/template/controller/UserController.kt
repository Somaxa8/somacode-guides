package com.somacode.template.controller

import com.somacode.template.entity.Authority
import com.somacode.template.entity.User
import com.somacode.template.security.SecurityTool
import com.somacode.template.service.UserService
import com.somacode.template.service.tool.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Validated
@RestController
class UserController {

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var securityTool: SecurityTool

    @PostMapping("/api/users/register")
    fun postRegister(
            @RequestParam @Email email: String,
            @RequestParam @Size(min = 4) password: String,
            @RequestParam @Size(min = 2) name: String
    ): ResponseEntity<User> {
        val user = userService.findById(securityTool.getUserId())
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userService.register(
                        email = email,
                        password = password,
                        name = name
                )
        )
    }

    @GetMapping("/api/users")
    fun getUsers(
            @RequestParam(required = false) search: String?,
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestParam role: Authority.Role
    ): ResponseEntity<List<User>> {
        val result: Page<User> = userService.findFilterPageable(page, size, search, role)
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
                .body(result.content)
    }

    @GetMapping("/api/users/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id))
    }

    @GetMapping("/api/users/@me")
    fun getMyUser(): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyUser())
    }

    @DeleteMapping("/api/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @PatchMapping("/api/users/{id}/change-password")
    fun patchUserChangePassword(
            @PathVariable id: Long,
            @RequestParam password: String,
            @RequestParam newPassword: String
    ): ResponseEntity<Void> {
        userService.changePassword(id, password, newPassword)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

}