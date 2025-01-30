package com.product.global.internal.order.stub

data class OrderResDto(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val cancelReason: OrderCancelReason?,
    val orderProducts: List<ProductDto>
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