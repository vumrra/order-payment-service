package com.order.domain.order.dto

import com.order.domain.order.persistence.OrderCancelReason
import com.order.domain.order.persistence.OrderStatus

data class OrderReqDto(
    val products: List<ProductDto>
)

data class OrderResDto(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val cancelReason: OrderCancelReason?,
    val orderProducts: List<ProductDto>
)

data class ProductDto(
    val productId: Long,
    val quantity: Int
)
