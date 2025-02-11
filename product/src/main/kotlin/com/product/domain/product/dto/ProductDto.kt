package com.product.domain.product.dto

import java.time.LocalDateTime

data class CategoryDto(
    val id: Long,
    val name: String,
)

data class ProductsDto(
    val count: Int,
    val categoryName: String,
    val products: List<ProductDto>
)

data class ProductDto(
    val productId: Long,
    val categoryId: Long,
    val categoryName: String,
    val name: String,
    val originPrice: Long,
    val isSale: Boolean,
    val saleInfo: SaleDto?,
    var quantity: Int,
    val orderCount: Long,
    val totalOrderQuantity: Int,
    val createdDate: LocalDateTime
)

data class SaleDto(
    val saleStartDate: LocalDateTime,
    val saleEndDate: LocalDateTime,
    val salePercentage: Int,
    val salePrice: Long,
)
