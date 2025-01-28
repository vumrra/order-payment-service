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
    @Enumerated(EnumType.STRING)
    var status: OrderStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(userId: Long): Order =
            Order(
                userId = userId,
                status = OrderStatus.PENDING,
                createdAt = LocalDateTime.now()
            )
    }
}

enum class OrderStatus {
    PENDING, CANCELLED, CONFIRMED
}