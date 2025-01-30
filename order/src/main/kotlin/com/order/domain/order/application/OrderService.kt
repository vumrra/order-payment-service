package com.order.domain.order.application

import com.order.domain.order.dto.OrderReqDto
import com.order.domain.order.persistence.OrderCancelReason

interface OrderService {
    fun order(dto: OrderReqDto)
    fun cancelOrder(orderId: Long, reason: OrderCancelReason)
}
