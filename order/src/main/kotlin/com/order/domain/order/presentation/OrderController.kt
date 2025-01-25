package com.order.domain.order.presentation

import com.order.domain.order.application.OrderService
import com.order.domain.order.dto.OrderReqDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

}
