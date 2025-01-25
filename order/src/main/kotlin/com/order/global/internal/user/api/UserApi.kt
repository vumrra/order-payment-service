package com.order.global.internal.user.api

import com.order.global.internal.user.stub.UserExistsStub
import com.order.global.internal.user.stub.UserStub

interface UserApi {
    fun queryById(userId: Long): UserStub
    fun exists(userId: Long): UserExistsStub
}