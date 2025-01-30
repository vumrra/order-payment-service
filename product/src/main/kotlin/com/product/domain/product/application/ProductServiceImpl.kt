package com.product.domain.product.application

import com.product.domain.product.event.OrderReservedEvent
import com.product.domain.product.event.ProductReservedEvent
import com.product.domain.product.persistence.Product
import com.product.domain.product.persistence.ProductRepository
import com.product.global.error.ProductException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductServiceImpl(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val productRepository: ProductRepository
) : ProductService {

    @Transactional
    override fun deductedProduct(event: OrderReservedEvent) {
        val products = event.products.map {
            val product = (productRepository.findByIdOrNull(it.productId)
                ?: throw ProductException("Product with id ${it.productId} not found", HttpStatus.NOT_FOUND))

            product.deduction(it.quantity)
            productRepository.save(product)
        }

        val totalPrice = products.sumOf { it.price }

        applicationEventPublisher.publishEvent(
            ProductReservedEvent(
                id = UUID.randomUUID().toString(),
                userId = event.userId,
                orderId = event.orderId,
                totalPrice = totalPrice
            )
        )

    }

}
