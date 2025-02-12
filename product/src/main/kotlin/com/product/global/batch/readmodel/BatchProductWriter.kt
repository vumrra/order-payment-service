package com.product.global.batch.readmodel

import com.product.domain.product.persistence.MongoProductRepository
import com.product.domain.product.persistence.ProductDocument
import com.product.global.internal.order.api.OrderApi
import com.product.global.internal.order.stub.ProductOrderInfoReqDto
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.data.mongodb.core.query.update
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component

@Component
class BatchProductWriter(
    private val mongoProductRepository: MongoProductRepository,
    private val orderApi: OrderApi
) : ItemWriter<ProductDocument> {

    override fun write(@NonNull items: Chunk<out ProductDocument>) {
        val productIds = items.map { it.productId }
        val orderCounts = orderApi.queryOrderCounts(ProductOrderInfoReqDto(productIds))

        mongoProductRepository.deleteAllById(productIds)

        if (orderCounts.isEmpty()) {
            mongoProductRepository.saveAll(items)
            return
        }

        val orderCountMap = orderCounts.associateBy { it.productId }

        val updatedProducts = items.map { productDocument ->
            val orderCount = orderCountMap[productDocument.productId]
            if (orderCount != null) {
                productDocument.updateOrderInfo(
                    orderCount = orderCount.count,
                    totalOrderQuantity = orderCount.totalQuantity
                )
            }
            productDocument
        }

        mongoProductRepository.saveAll(updatedProducts)
    }
}
