package com.user.domain.user.presentation

import com.user.domain.user.application.UserService
import com.user.domain.user.dto.UserInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun queryUserInfo(): ResponseEntity<UserInfoDto> {
        val response = userService.queryUserInfo()
        return ResponseEntity.ok(response)
    }

}