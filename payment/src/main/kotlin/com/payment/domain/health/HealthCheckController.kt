package com.payment.domain.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment")
class HealthCheckController {

    @GetMapping("/health")
    fun healthCheck() = "Payment Service OK"

}
