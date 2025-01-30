package com.payment.global.user.api

import com.payment.global.user.stub.UserExistsStub
import com.payment.global.user.stub.UserStub

interface UserApi {
    fun queryById(userId: Long): UserStub
    fun exists(userId: Long): UserExistsStub
}