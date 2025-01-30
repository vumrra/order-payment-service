package com.product.domain.product.dto

import java.time.LocalDateTime

data class ProductAllDto(
    val count: Int,
    val products: List<ProductDto>
)

data class ProductDto(
    val productId: Long,
    val name: String,
    val price: Long,
    var quantity: Int,
    val createdDate: LocalDateTime
)
