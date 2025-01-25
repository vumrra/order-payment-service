package com.user.domain.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class HealthCheckController {

    @GetMapping("/health")
    fun healthCheck() = "User Service OK"

}
