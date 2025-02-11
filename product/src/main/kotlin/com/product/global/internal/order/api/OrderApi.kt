package com.product.global.internal.order.api

import com.product.global.internal.order.stub.OrderResDto
import com.product.global.internal.order.stub.ProductOrderCountDto

interface OrderApi {
    fun queryById(orderId: Long): OrderResDto
    fun queryOrderCount(productId: Long): ProductOrderCountDto
}