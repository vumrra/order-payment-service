package com.user.domain.auth.application

import com.user.domain.auth.dto.LoginReqDto
import com.user.domain.auth.dto.LoginResDto
import com.user.domain.auth.dto.SignupDto

interface AuthService {
    fun signup(dto: SignupDto)
    fun login(dto: LoginReqDto): LoginResDto
}