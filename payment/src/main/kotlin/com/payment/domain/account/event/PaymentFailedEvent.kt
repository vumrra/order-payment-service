package com.payment.domain.account.event

data class PaymentFailedEvent(
    val id: String,
    val orderId: Long
)
