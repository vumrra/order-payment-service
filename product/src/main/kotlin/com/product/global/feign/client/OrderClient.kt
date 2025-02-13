package com.product.global.feign.client

import com.product.global.internal.order.stub.OrderResDto
import com.product.global.internal.order.stub.ProductOrderCountDto
import com.product.global.internal.order.stub.ProductOrderInfoReqDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "order-service")
interface OrderClient {
    @GetMapping("/order/{order_id}")
    fun queryById(
        @PathVariable("order_id") orderId: Long
    ): OrderResDto

    @PostMapping("/order/order-count")
    fun queryOrderCount(
        @RequestBody dto: ProductOrderInfoReqDto
    ): List<ProductOrderCountDto>
}
