package com.product.global.internal.user.api

import com.product.global.feign.client.UserClient
import com.product.global.internal.user.stub.UserExistsStub
import com.product.global.internal.user.stub.UserStub
import org.springframework.stereotype.Component

@Component
class StudentApiImpl(
    private val userClient: UserClient
) : UserApi {

    override fun queryById(userId: Long): UserStub =
        userClient.queryById(userId)

    override fun exists(userId: Long): UserExistsStub =
        userClient.exists(userId)

}
