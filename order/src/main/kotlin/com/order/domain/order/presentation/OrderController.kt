package com.order.domain.order.presentation

import com.order.domain.order.application.OrderService
import com.order.domain.order.dto.OrderReqDto
import com.order.domain.order.dto.OrderResDto
import com.order.domain.order.dto.ProductOrderCount
import com.order.domain.order.persistence.OrderRepository
import com.order.domain.order.persistence.dto.ProductOrderInfoReqDto
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

    @PostMapping("/order-count")
    fun queryProductOrderCount(
        @RequestBody productOrderInfoReqDto: ProductOrderInfoReqDto
    ): ResponseEntity<List<ProductOrderCount>> {

        val orderProducts = orderRepository.findOrderProductInfoByProductIds(productOrderInfoReqDto.productIds).map {
            ProductOrderCount(
                productId = it.productId,
                count = it.count.toInt(),
                totalQuantity = it.totalQuantity.toInt()
            )
        }

        return ResponseEntity.ok(orderProducts)
    }

}
