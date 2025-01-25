package com.order.domain.order.dto

import com.order.domain.order.persistence.OrderProduct

data class OrderReqDto(
    val products: List<OrderProduct>
)

data class ProductDto(
    val productId: Long,
    val quantity: Int
)
