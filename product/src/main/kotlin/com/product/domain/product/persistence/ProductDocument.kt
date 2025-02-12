package com.product.domain.product.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "products")
data class ProductDocument(
    @Id
    val productId: Long,
    val categoryId: Long,
    val categoryName: String,
    val name: String,
    val originPrice: Long,
    val quantity: Int,
    val isSale: Boolean,
    var orderCount: Long,
    var totalOrderQuantity: Int,
    val saleInfo: SaleDocument? = null,
    val createdDate: LocalDateTime
) {
    fun updateOrderInfo(orderCount: Long, totalOrderQuantity: Int) {
        this.orderCount = orderCount
        this.totalOrderQuantity = totalOrderQuantity
    }
}

data class SaleDocument(
    val saleStartDate: LocalDateTime,
    val saleEndDate: LocalDateTime,
    val salePercentage: Int,
    val salePrice: Long,
)
