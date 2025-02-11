package com.order.domain.order.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {
    @Query(
        "SELECT op FROM Order o JOIN OrderProduct op ON op.order.id = o.id WHERE op.productId = :productId"
    )
    fun orderProductByProductId(productId: Long): List<OrderProduct>
}
