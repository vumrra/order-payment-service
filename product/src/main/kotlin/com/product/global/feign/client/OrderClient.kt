package com.product.global.feign.client

import com.payment.global.internal.product.stub.OrderResDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "order-service")
interface OrderClient {
    @GetMapping("/order/{order_id}")
    fun queryById(
        @PathVariable("order_id") orderId: Long
    ): OrderResDto
}
