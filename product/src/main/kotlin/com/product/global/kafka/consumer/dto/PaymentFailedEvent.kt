package com.product.global.kafka.consumer.dto

data class PaymentFailedEvent(
    val id: String,
    val orderId: Long
)
