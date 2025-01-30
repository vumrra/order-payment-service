package com.order.global.kafka.consumer.dto

data class PaymentSuccessEvent(
    val id: String,
    val orderId: Long
)
