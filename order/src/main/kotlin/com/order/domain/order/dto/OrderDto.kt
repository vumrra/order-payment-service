package com.order.domain.order.dto

data class OrderReqDto(
    val products: List<ProductDto>
)

data class ProductDto(
    val productId: Long,
    val quantity: Int
)
