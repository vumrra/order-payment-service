package com.product.domain.product.event

data class ProductReservedEvent(
    val id: String,
    val userId: Long,
    val orderId: Long,
    val totalPrice: Int
)
