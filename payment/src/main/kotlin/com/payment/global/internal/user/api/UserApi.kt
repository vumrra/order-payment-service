package com.payment.global.internal.user.api

import com.payment.global.internal.user.stub.UserExistsStub
import com.payment.global.internal.user.stub.UserStub

interface UserApi {
    fun queryById(userId: Long): UserStub
    fun exists(userId: Long): UserExistsStub
}