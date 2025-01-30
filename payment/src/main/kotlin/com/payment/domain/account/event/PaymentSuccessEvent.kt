package com.payment.domain.account.event

data class PaymentSuccessEvent(
    val id: String,
    val orderId: Long
)
