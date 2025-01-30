package com.payment.global.util

import com.payment.global.user.api.UserApi
import com.payment.global.user.stub.UserStub
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserContextUtil(
    private val userApi: UserApi
) {

    fun getCurrentUserId(): Long {
        return SecurityContextHolder.getContext().authentication.name.toLong()
    }

    fun getCurrentUser(): UserStub {
        val student = userApi.queryById(getCurrentUserId())
        return student
    }
}
