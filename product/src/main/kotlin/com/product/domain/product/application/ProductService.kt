package com.product.domain.product.application

import com.product.global.kafka.consumer.dto.OrderReservedEvent

interface ProductService {
    fun deductedProduct(event: OrderReservedEvent)
}