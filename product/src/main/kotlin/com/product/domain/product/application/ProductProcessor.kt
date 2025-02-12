package com.product.domain.product.application

import com.product.domain.product.event.ProductCreatedEvent
import com.product.domain.product.persistence.MongoProductRepository
import com.product.domain.product.persistence.ProductDocument
import com.product.global.internal.order.api.OrderApi
import com.product.global.internal.order.stub.ProductOrderInfoReqDto
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProductProcessor(
    private val mongoProductRepository: MongoProductRepository,
    private val orderApi: OrderApi
) {

    @Transactional
    fun createProductReadModel(event: ProductCreatedEvent) {
        val productDocument = event.let {
            val orderCount = orderApi.queryOrderCounts(
                ProductOrderInfoReqDto(listOf(event.productId))
            )

            ProductDocument(
                productId = it.productId,
                name = it.name,
                categoryId = it.categoryId,
                categoryName = it.categoryName,
                originPrice = it.price,
                quantity = it.quantity,
                createdDate = it.createdDate,
                isSale = false,
                orderCount = if (orderCount.isNotEmpty()) orderCount[0].count else 0,
                totalOrderQuantity = if (orderCount.isNotEmpty()) orderCount[0].totalQuantity else 0
            )
        }

        mongoProductRepository.save(productDocument)
    }

}
