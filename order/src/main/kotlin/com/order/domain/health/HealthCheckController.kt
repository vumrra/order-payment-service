package com.order.domain.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class HealthCheckController {

    @GetMapping("/health")
    fun healthCheck() = "Order Service OK"

}