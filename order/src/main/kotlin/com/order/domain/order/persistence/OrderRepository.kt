package com.order.domain.order.persistence

import com.order.domain.order.persistence.dto.ProductOrderInfoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {
    @Query(
        """
                SELECT new com.order.domain.order.persistence.dto.ProductOrderInfoDto(
                    op.productId, 
                    COUNT(op), 
                    SUM(op.quantity)
                )
                FROM OrderProduct op
                JOIN op.order o
                WHERE op.productId IN :productIds
                GROUP BY op.productId
        """
    )
    fun findOrderProductInfoByProductIds(productIds: List<Long>): List<ProductOrderInfoDto>
}
