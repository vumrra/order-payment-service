package com.user.domain.user.dto

import com.user.domain.user.persistence.Authority
import com.user.domain.user.persistence.Sex

data class UserInfoDto(
    val userId: Long,
    val name: String,
    val email: String,
    val sex: Sex,
    val authority: Authority
)
