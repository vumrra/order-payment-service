package com.product.global.internal.user.api

import com.product.global.internal.user.stub.UserExistsStub
import com.product.global.internal.user.stub.UserStub

interface UserApi {
    fun queryById(userId: Long): UserStub
    fun exists(userId: Long): UserExistsStub
}