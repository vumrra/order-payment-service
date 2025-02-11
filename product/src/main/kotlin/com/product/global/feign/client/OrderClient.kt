package com.product.global.feign.client

import com.product.global.internal.order.stub.OrderResDto
import com.product.global.internal.order.stub.ProductOrderCountDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "order-service")
interface OrderClient {
    @GetMapping("/order/{order_id}")
    fun queryById(
        @PathVariable("order_id") orderId: Long
    ): OrderResDto

    @GetMapping("/order/order-count")
    fun queryOrderCount(
        @RequestParam("productId") productId: Long
    ): ProductOrderCountDto
}
