package com.product.domain.product.application

import com.product.domain.product.dto.ProductDto
import com.product.domain.product.dto.ProductsDto
import com.product.domain.product.dto.SaleDto
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
import java.time.LocalDateTime
import java.util.*

@Service
class ProductServiceImpl(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val productRepository: ProductRepository,
    private val orderApi: OrderApi
) : ProductService {

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
    }

    @Transactional(readOnly = true)
    override fun queryProducts(categoryId: Long): ProductsDto {
        val products = productRepository.findByCategoryId(categoryId)

        val productDto = products.map { product ->
            val orderCount = orderApi.queryOrderCount(product.id)

            ProductDto(
                productId = product.id,
                categoryId = product.category.id,
                categoryName = product.category.name,
                name = product.name,
                originPrice = product.price,
                quantity = product.quantity,
                isSale = product.sale != null,
                orderCount = orderCount.count,
                totalOrderQuantity = orderCount.totalQuantity,
                saleInfo = product.sale?.let {
                    SaleDto(
                        salePercentage = it.percentage,
                        salePrice = product.price * it.percentage / 100,
                        saleStartDate = it.startDate,
                        saleEndDate = it.endDate,
                    )
                },
                createdDate = LocalDateTime.now(),
            )
        }

        return ProductsDto(
            count = productDto.size,
            categoryName = products[0].category.name,
            products = productDto
        )
    }

    @Transactional(readOnly = true)
    override fun queryProduct(productId: Long): ProductDto {
        val product = (productRepository.findByIdOrNull(productId)
            ?: throw ProductException("Product with id ${productId} not found", HttpStatus.NOT_FOUND))

        val productOrderCount = orderApi.queryOrderCount(productId)

         return product.let { p ->
            ProductDto(
                productId = p.id,
                categoryId = p.category.id,
                categoryName = p.category.name,
                name = p.name,
                originPrice = p.price,
                quantity = p.quantity,
                isSale = p.sale != null,
                orderCount = productOrderCount.count,
                totalOrderQuantity = productOrderCount.totalQuantity,
                saleInfo = p.sale?.let {
                    SaleDto(
                        salePercentage = it.percentage,
                        salePrice = p.price * it.percentage / 100,
                        saleStartDate = it.startDate,
                        saleEndDate = it.endDate,
                    )
                },
                createdDate = LocalDateTime.now(),
            )
        }
    }

}
