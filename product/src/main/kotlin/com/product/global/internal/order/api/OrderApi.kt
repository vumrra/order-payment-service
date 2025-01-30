package com.product.global.internal.order.api

import com.product.global.internal.order.stub.OrderResDto

interface OrderApi {
    fun queryById(orderId: Long): OrderResDto
}