package com.order.domain.order.persistence

import jakarta.persistence.*

@Entity
@Table(name = "tbl_order_product")
class OrderProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val productId: Long,
    val quantity: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order
) {
    companion object {
        fun of(order: Order, productId: Long, quantity: Int): OrderProduct =
            OrderProduct(
                order = order,
                productId = productId,
                quantity = quantity,
            )
    }
}
