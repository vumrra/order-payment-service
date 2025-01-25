package com.user.global.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "security.jwt.token")
class JwtProperties(
    val accessSecret: String,
    val refreshSecret: String,
    val accessExp: Long,
    val refreshExp: Long
)