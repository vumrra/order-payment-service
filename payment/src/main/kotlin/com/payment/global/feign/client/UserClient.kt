package com.payment.global.feign.client

import com.payment.global.internal.user.stub.UserExistsStub
import com.payment.global.internal.user.stub.UserStub
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "user-service")
interface UserClient {
    @GetMapping("/user/exists")
    fun exists(
        @RequestParam("userId") userId: Long
    ): UserExistsStub

    @GetMapping("/user")
    fun queryById(
        @RequestParam("userId") userId: Long
    ): UserStub
}
