package com.order.domain.order.persistence

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_order")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    val id: Long = 0,
    val userId: Long,
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val orderProducts: List<OrderProduct> = emptyList(),
    val totalPrice: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(userId: Long, totalPrice: Int, orderProducts: List<OrderProduct>): Order =
            Order(
                userId = userId,
                orderProducts = orderProducts,
                totalPrice = totalPrice,
                createdAt = LocalDateTime.now()
            )
    }
}
