package com.product.domain.product.application

import com.product.domain.product.event.ProductCreatedEvent
import com.product.domain.product.persistence.MongoProductRepository
import com.product.domain.product.persistence.ProductDocument
import com.product.global.error.ProductException
import com.product.global.internal.order.api.OrderApi
import com.product.global.internal.order.stub.OrderResDto
import com.product.global.internal.order.stub.ProductDto
import com.product.global.internal.order.stub.ProductOrderInfoReqDto
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.global.kafka.consumer.dto.ProductEvent
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProductProcessor(
    private val mongoProductRepository: MongoProductRepository,
    private val orderApi: OrderApi
) {

    @Async("readModelTaskExecutor")
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

    @Async("readModelTaskExecutor")
    @Transactional
    fun updateProductQuantityReadModel(event: List<ProductDto>) {
        event.forEach {
            val productDocument = (mongoProductRepository.findByIdOrNull(it.productId)
                ?: throw ProductException(
                    "Product with id ${it.productId} not found from MongoDB",
                    HttpStatus.NOT_FOUND
                ))

            productDocument.updateQuantity(
                productDocument.quantity - it.quantity
            )
            productDocument.updateOrderInfo(
                productDocument.orderCount + 1,
                productDocument.quantity + it.quantity,
            )

            mongoProductRepository.save(productDocument)
        }
    }

    @Async("readModelTaskExecutor")
    @Transactional
    fun updateProductQuantityReadModel(event: List<ProductEvent>) {
        event.forEach {
            val productDocument = (mongoProductRepository.findByIdOrNull(it.productId)
                ?: throw ProductException(
                    "Product with id ${it.productId} not found from MongoDB",
                    HttpStatus.NOT_FOUND
                ))

            productDocument.updateQuantity(
                productDocument.quantity - it.quantity
            )
            productDocument.updateOrderInfo(
                productDocument.orderCount + 1,
                productDocument.quantity + it.quantity,
            )

            mongoProductRepository.save(productDocument)
        }
    }

}
