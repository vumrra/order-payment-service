package com.order.domain.order.persistence.dto

data class ProductOrderInfoDto(
    val productId: Long,
    val count: Long,
    val totalQuantity: Long
)

data class ProductOrderInfoReqDto(
    val productIds: List<Long>,
)
