package com.user.domain.user.application

import com.user.domain.user.dto.UserExistsDto
import com.user.domain.user.dto.UserInfoDto

interface UserService {
    fun queryById(userId: Long): UserInfoDto
    fun existsById(userId: Long): UserExistsDto
}