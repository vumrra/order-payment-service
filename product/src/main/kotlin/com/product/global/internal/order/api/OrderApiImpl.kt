package com.product.global.internal.order.api

import com.product.global.feign.client.OrderClient
import com.product.global.internal.order.stub.ProductOrderInfoReqDto
import org.springframework.stereotype.Component

@Component
class OrderApiImpl(
    private val orderClient: OrderClient
) : OrderApi {

    override fun queryById(orderId: Long) =
        orderClient.queryById(orderId)

    override fun queryOrderCounts(dto: ProductOrderInfoReqDto) =
        orderClient.queryOrderCount(dto)

}
