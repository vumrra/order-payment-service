package com.order.domain.order.event

data class ProductReserveFailedEvent(
    val id: String,
    val userId: Long,
    val orderId: Long
)
