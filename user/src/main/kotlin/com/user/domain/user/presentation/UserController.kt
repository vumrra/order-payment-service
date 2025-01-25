package com.user.domain.user.presentation

import com.user.domain.user.application.UserService
import com.user.domain.user.dto.UserExistsDto
import com.user.domain.user.dto.UserInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun queryById(
        @RequestParam("userId") userId: Long
    ): ResponseEntity<UserInfoDto> {
        val response = userService.queryById(userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/exists")
    fun existsById(
        @RequestParam("userId") userId: Long
    ): ResponseEntity<UserExistsDto> {
        val response = userService.existsById(userId)
        return ResponseEntity.ok(response)
    }

}
