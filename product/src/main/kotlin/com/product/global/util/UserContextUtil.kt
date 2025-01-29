package com.product.global.util

import com.product.global.internal.user.api.UserApi
import com.product.global.internal.user.stub.UserStub
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
