package com.order.domain.order.event

data class PaymentSuccessEvent(
    val id: String,
    val orderId: Long
)
