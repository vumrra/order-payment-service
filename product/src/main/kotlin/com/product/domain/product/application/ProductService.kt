package com.product.domain.product.application

import com.product.domain.product.event.OrderReservedEvent

interface ProductService {
    fun deductedProduct(event: OrderReservedEvent)
}