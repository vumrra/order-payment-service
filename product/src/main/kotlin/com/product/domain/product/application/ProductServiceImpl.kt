package com.product.domain.product.application

import com.product.domain.product.dto.ProductAllDto
import com.product.domain.product.dto.ProductDto
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.domain.product.event.ProductReservedEvent
import com.product.domain.product.persistence.ProductRepository
import com.product.global.error.ProductException
import com.product.global.internal.order.api.OrderApi
import com.product.global.kafka.consumer.dto.PaymentFailedEvent
import com.product.global.lock.DistributedLock
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.util.*

@Service
class ProductServiceImpl(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val productRepository: ProductRepository,
    private val orderApi: OrderApi
) : ProductService {

//    @DistributedLock(key = "'reserve_product_' + #event.userId")
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

        sleep(2000)

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
    }

    @Transactional(readOnly = true)
    override fun queryAllProducts(): ProductAllDto {
        val products = productRepository.findAll().map {
            ProductDto(
                productId = it.id,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                createdDate = LocalDateTime.now(),
            )
        }

        return ProductAllDto(
            count = products.size,
            products = products
        )
    }

}
