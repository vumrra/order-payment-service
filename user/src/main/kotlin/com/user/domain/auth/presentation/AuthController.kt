package com.user.domain.auth.presentation

import com.user.domain.auth.application.AuthService
import com.user.domain.auth.dto.LoginReqDto
import com.user.domain.auth.dto.LoginResDto
import com.user.domain.auth.dto.SignupDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(
        @RequestBody dto: SignupDto
    ): ResponseEntity<Void> {
        authService.signup(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody dto: LoginReqDto
    ): ResponseEntity<LoginResDto> {
        val response = authService.login(dto)
        return ResponseEntity.ok(response)
    }

}
