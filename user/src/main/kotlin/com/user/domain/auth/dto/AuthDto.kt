package com.user.domain.auth.dto

import com.user.domain.user.persistence.Sex

data class SignupDto(
    val email: String,
    val password: String,
    val name: String,
    val sex: Sex
)

data class LoginReqDto(
    val email: String,
    val password: String,
)

data class LoginResDto(
    val accessToken: String,
    val refreshToken: String
)