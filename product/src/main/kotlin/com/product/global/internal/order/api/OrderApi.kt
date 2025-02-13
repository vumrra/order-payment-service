package com.product.global.internal.order.api

import com.product.global.internal.order.stub.OrderResDto
import com.product.global.internal.order.stub.ProductOrderCountDto
import com.product.global.internal.order.stub.ProductOrderInfoReqDto

interface OrderApi {
    fun queryById(orderId: Long): OrderResDto
    fun queryOrderCounts(dto: ProductOrderInfoReqDto): List<ProductOrderCountDto>
}
