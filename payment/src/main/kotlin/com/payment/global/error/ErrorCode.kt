package com.payment.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus
) {
    BAD_REQUEST("Bad Request", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
}
