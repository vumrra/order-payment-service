package com.product.domain.product.application

import com.product.domain.product.dto.ProductDto
import com.product.domain.product.dto.ProductsDto
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.global.kafka.consumer.dto.PaymentFailedEvent

interface ProductService {
    fun deductedProduct(event: OrderReservedEvent)
    fun rollbackProduct(event: PaymentFailedEvent)
    fun queryProducts(categoryId: Long): ProductsDto
    fun queryProduct(productId: Long): ProductDto
}
