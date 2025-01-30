package com.order.domain.order.application

import com.order.domain.order.dto.OrderReqDto
import com.order.domain.order.event.OrderReservedEvent
import com.order.domain.order.event.ProductEvent
import com.order.domain.order.persistence.*
import com.order.global.error.OrderException
import com.order.global.util.UserContextUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OrderServiceImpl(
    private val userContextUtil: UserContextUtil,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository
) : OrderService {

    @Transactional
    override fun order(dto: OrderReqDto) {
        val currentUserId = userContextUtil.getCurrentUserId()

        val order = Order.of(currentUserId)
        val savedOrder = orderRepository.save(order)

        val products = dto.products.map {
            OrderProduct.of(
                order = savedOrder,
                productId = it.productId,
                quantity = it.quantity
            )
        }
        orderProductRepository.saveAll(products)

        applicationEventPublisher.publishEvent(
            OrderReservedEvent(
                id = UUID.randomUUID().toString(),
                orderId = order.id,
                userId = currentUserId,
                depositDestination = "주문의 민족",
                products = products.map {
                    ProductEvent(
                        productId = it.productId,
                        quantity = it.quantity,
                    )
                }
            )
        )

    }

    @Transactional
    override fun cancel(orderId: Long, reason: OrderCancelReason) {
        val order = (orderRepository.findByIdOrNull(orderId)
            ?: throw OrderException("Not Found Order with id: $orderId", HttpStatus.NOT_FOUND))

        order.cancel(reason)
        orderRepository.save(order)
    }

    @Transactional
    override fun confirm(orderId: Long) {
        val order = (orderRepository.findByIdOrNull(orderId)
            ?: throw OrderException("Not Found Order with id: $orderId", HttpStatus.NOT_FOUND))

        order.confirm()
        orderRepository.save(order)
    }

}
