package com.product.global.error

import org.springframework.http.HttpStatus

open class ProductException(
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message)
