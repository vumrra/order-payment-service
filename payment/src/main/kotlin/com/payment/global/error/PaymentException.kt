package com.payment.global.error

import org.springframework.http.HttpStatus

open class PaymentException(
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message)
