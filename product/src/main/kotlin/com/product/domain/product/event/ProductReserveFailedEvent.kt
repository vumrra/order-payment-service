package com.product.domain.product.event

data class ProductReserveFailedEvent(
    val id: String,
    val userId: Long,
    val orderId: Long
)
