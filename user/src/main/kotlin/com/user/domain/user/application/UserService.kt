package com.user.domain.user.application

import com.user.domain.user.dto.UserInfoDto

interface UserService {
    fun queryUserInfo(): UserInfoDto
}