package com.user.global.error

open class UserException(
    override val message: String,
    val status: Int
) : RuntimeException(message)