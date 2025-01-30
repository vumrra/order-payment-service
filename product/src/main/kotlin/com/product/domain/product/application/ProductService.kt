package com.product.domain.product.application

import com.product.domain.product.dto.ProductAllDto
import com.product.global.kafka.consumer.dto.OrderReservedEvent

interface ProductService {
    fun deductedProduct(event: OrderReservedEvent)
    fun queryAllProducts(): ProductAllDto
}
