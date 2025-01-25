package com.user.global.error

import org.springframework.http.HttpStatus

open class UserException(
    override val message: String,
    val status: HttpStatus
) : RuntimeException(message)