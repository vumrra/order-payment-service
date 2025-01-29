package com.product.domain.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class HealthCheckController {

    @GetMapping("/health")
    fun healthCheck() = "Product Service OK"

}