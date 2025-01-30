package com.order.domain.order.event

data class PaymentFailedEvent(
    val id: String,
    val orderId: Long
)
