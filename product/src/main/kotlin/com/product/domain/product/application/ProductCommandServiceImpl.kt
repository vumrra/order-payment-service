package com.product.domain.product.application

import com.product.domain.product.dto.CreateProductDto
import com.product.domain.product.event.ProductCreatedEvent
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.domain.product.event.ProductReservedEvent
import com.product.domain.product.persistence.*
import com.product.global.error.ProductException
import com.product.global.internal.order.api.OrderApi
import com.product.global.kafka.consumer.dto.PaymentFailedEvent
import com.product.global.lock.DistributedLock
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductCommandServiceImpl(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val orderApi: OrderApi,
    private val productProcessor: ProductProcessor
) : ProductCommandService {

    @DistributedLock(key = "'reserve_product_' + #event.products.![productId]")
    @Transactional
    override fun deductedProduct(event: OrderReservedEvent) {

        var totalPrice = 0L

        event.products.forEach {
            val product = (productRepository.findByIdOrNull(it.productId)
                ?: throw ProductException("Product with id ${it.productId} not found", HttpStatus.NOT_FOUND))

            totalPrice += product.price * it.quantity

            product.deduction(it.quantity)
            productRepository.save(product)
        }

        applicationEventPublisher.publishEvent(
            ProductReservedEvent(
                id = UUID.randomUUID().toString(),
                userId = event.userId,
                orderId = event.orderId,
                depositDestination = event.depositDestination,
                totalPrice = totalPrice
            )
        )

    }

    @Transactional
    override fun rollbackProduct(event: PaymentFailedEvent) {
        val orderDto = orderApi.queryById(event.orderId)

        orderDto.orderProducts.forEach {
            val product = (productRepository.findByIdOrNull(it.productId)
                ?: throw ProductException("Product with id ${it.productId} not found", HttpStatus.NOT_FOUND))

            product.add(it.quantity)
            productRepository.save(product)
        }

        // Async
        productProcessor.updateProductQuantityReadModel(orderDto.orderProducts)
    }

    @Transactional
    override fun createProduct(dto: CreateProductDto): Long {
        val category = (categoryRepository.findByIdOrNull(dto.categoryId)
            ?: throw ProductException("Category with id ${dto.categoryId} not found", HttpStatus.NOT_FOUND))

        val product = dto.let {
            Product.of(
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                category = category,
            )
        }

        val savedProduct = productRepository.save(product)

        applicationEventPublisher.publishEvent(
            ProductCreatedEvent(
                categoryId = category.id,
                categoryName = category.name,
                productId = savedProduct.id,
                name = savedProduct.name,
                price = savedProduct.price,
                quantity = savedProduct.quantity,
                createdDate = savedProduct.createdDate,
            )
        )

        return savedProduct.id
    }
}
