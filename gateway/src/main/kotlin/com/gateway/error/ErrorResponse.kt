package com.gateway.error

data class ErrorResponse(
    val message: String
) {
    companion object {
        fun of(message: String): ErrorResponse =
            ErrorResponse(message)
    }
}