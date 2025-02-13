package com.product.domain.product.application

import com.product.domain.product.dto.CreateProductDto
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.global.kafka.consumer.dto.PaymentFailedEvent

interface ProductCommandService {
    fun deductedProduct(event: OrderReservedEvent)
    fun rollbackProduct(event: PaymentFailedEvent)
    fun createProduct(dto: CreateProductDto): Long
}
