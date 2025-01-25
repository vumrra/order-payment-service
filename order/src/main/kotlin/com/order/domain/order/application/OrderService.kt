package com.order.domain.order.application

import com.order.domain.order.dto.OrderReqDto

interface OrderService {
    fun order(dto: OrderReqDto)
}
