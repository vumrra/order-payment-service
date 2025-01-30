package com.order.global.kafka.consumer.dto

data class ProductReserveFailedEvent(
    val id: String,
    val userId: Long,
    val orderId: Long
)
