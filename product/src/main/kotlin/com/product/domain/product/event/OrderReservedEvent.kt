package com.product.domain.product.event

data class OrderReservedEvent(
    val id: String,
    val orderId: Long,
    val userId: Long,
    val products: List<ProductEvent>
)

data class ProductEvent(
    val productId: Long,
    val quantity: Int
)