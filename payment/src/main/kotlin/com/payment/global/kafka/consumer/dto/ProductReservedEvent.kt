package com.payment.global.kafka.consumer.dto

data class ProductReservedEvent(
    val id: String,
    val userId: Long,
    val orderId: Long,
    val depositDestination: String,
    val totalPrice: Long
)
