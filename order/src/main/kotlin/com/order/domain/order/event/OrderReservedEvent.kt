package com.order.domain.order.event

data class OrderReservedEvent(
    val id: String,
    val orderId: Long,
    val userId: Long,
    val depositDestination: String,
    val products: List<ProductEvent>
)

data class ProductEvent(
    val productId: Long,
    val quantity: Int
)