package com.order.global.error

import org.springframework.http.HttpStatus

open class OrderException(
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message)
