package com.order.domain.order.application

import com.order.domain.order.dto.OrderReqDto
import com.order.domain.order.dto.OrderResDto
import com.order.domain.order.persistence.OrderCancelReason

interface OrderService {
    fun order(dto: OrderReqDto)
    fun cancel(orderId: Long, reason: OrderCancelReason)
    fun confirm(orderId: Long)
    fun queryById(orderId: Long): OrderResDto
}
