package com.order.domain.order.presentation

import com.order.domain.order.application.OrderService
import com.order.domain.order.dto.OrderReqDto
import com.order.domain.order.dto.OrderResDto
import com.order.domain.order.dto.ProductOrderCount
import com.order.domain.order.persistence.OrderRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
    private val orderRepository: OrderRepository,
) {

    @PostMapping
    fun order(
        @RequestBody dto: OrderReqDto
    ): ResponseEntity<Void> {
        orderService.order(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/{order_id}")
    fun queryOrderInfo(
        @PathVariable("order_id") orderId: Long
    ): ResponseEntity<OrderResDto> {
        val response = orderService.queryById(orderId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/order-count")
    fun queryProductOrderCount(
        @RequestParam("productId") productId: Long
    ): ResponseEntity<ProductOrderCount> {

        val orderProducts = orderRepository.orderProductByProductId(productId)
        var totalQuantity = 0

        orderProducts.forEach { order ->
            totalQuantity += order.quantity
        }

        return ResponseEntity.ok(ProductOrderCount(
            count = orderProducts.size,
            totalQuantity = totalQuantity
        ))
    }

}
