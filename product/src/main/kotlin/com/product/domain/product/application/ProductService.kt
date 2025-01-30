package com.product.domain.product.application

import com.product.domain.product.dto.ProductAllDto
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.global.kafka.consumer.dto.PaymentFailedEvent

interface ProductService {
    fun deductedProduct(event: OrderReservedEvent)
    fun rollbackProduct(event: PaymentFailedEvent)
    fun queryAllProducts(): ProductAllDto
}
