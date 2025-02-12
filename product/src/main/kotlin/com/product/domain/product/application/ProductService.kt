package com.product.domain.product.application

import com.product.domain.product.dto.CreateProductDto
import com.product.domain.product.dto.ProductsDto
import com.product.domain.product.persistence.ProductDocument
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.global.kafka.consumer.dto.PaymentFailedEvent

interface ProductService {
    fun deductedProduct(event: OrderReservedEvent)
    fun rollbackProduct(event: PaymentFailedEvent)
    fun createProduct(dto: CreateProductDto): Long
    fun queryProducts(categoryId: Long): ProductsDto
    fun queryProduct(productId: Long): ProductDocument
}
