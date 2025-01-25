package com.order.global.feign.client

import com.order.global.internal.user.stub.UserExistsStub
import com.order.global.internal.user.stub.UserStub
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
