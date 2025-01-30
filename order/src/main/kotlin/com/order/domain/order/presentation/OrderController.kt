package com.order.domain.order.presentation

import com.order.domain.order.application.OrderService
import com.order.domain.order.dto.OrderReqDto
import com.order.domain.order.dto.OrderResDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
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

}
