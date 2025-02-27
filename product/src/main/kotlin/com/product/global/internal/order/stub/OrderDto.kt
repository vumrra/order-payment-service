package com.product.global.internal.order.stub

data class OrderResDto(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val cancelReason: OrderCancelReason?,
    val orderProducts: List<ProductDto>
)

data class ProductOrderCountDto(
    val productId: Long,
    val count: Long,
    val totalQuantity: Int
)

data class ProductOrderInfoReqDto(
    val productIds: List<Long>,
)

data class ProductDto(
    val productId: Long,
    val quantity: Int
)

enum class OrderStatus {
    PENDING, CANCELLED, CONFIRMED
}

enum class OrderCancelReason {
    OUT_OF_STOCK, PAYMENT_FAILED
}