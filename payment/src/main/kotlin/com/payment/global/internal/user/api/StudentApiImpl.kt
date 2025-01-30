package com.payment.global.internal.user.api

import com.payment.global.feign.client.UserClient
import com.payment.global.internal.user.stub.UserExistsStub
import com.payment.global.internal.user.stub.UserStub
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
