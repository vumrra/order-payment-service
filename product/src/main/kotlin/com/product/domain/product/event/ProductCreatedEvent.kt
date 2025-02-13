package com.product.domain.product.event

import java.time.LocalDateTime

data class ProductCreatedEvent(
    val categoryId: Long,
    val categoryName: String,
    val productId: Long,
    val name: String,
    val price: Long,
    val quantity: Int,
    val createdDate: LocalDateTime
)
