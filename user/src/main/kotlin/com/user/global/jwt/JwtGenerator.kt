package com.user.global.jwt

import com.user.domain.user.persistence.Authority
import com.user.global.jwt.dto.TokenDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.LocalDateTime
import java.util.*

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties
) {

    companion object {
        const val AUTHORITY = "authority"
        const val TOKEN_TYPE = "tokenType"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val REFRESH_TOKEN_HEADER = "Refresh-Token"
    }

    fun generateToken(userId: String, authority: Authority): TokenDto =
        TokenDto(
            accessToken = generateAccessToken(userId, authority),
            refreshToken = generateRefreshToken(userId),
            accessTokenExp = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
            refreshTokenExp = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp)
        )

    fun getUserIdFromRefreshToken(token: String): String {
        return getRefreshTokenSubject(token)
    }

    private fun generateAccessToken(userId: String, authority: Authority): String =
        Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtProperties.accessSecret.toByteArray(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
            .setSubject(userId)
            .claim(TOKEN_TYPE, ACCESS_TOKEN)
            .claim(AUTHORITY, authority)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * 1000))
            .compact()

    private fun generateRefreshToken(userId: String): String =
        Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtProperties.refreshSecret.toByteArray(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
            .setSubject(userId)
            .claim(TOKEN_TYPE, REFRESH_TOKEN)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * 1000))
            .compact()

    private fun getRefreshTokenSubject(refreshToken: String): String {
        return getTokenBody(refreshToken, Keys.hmacShaKeyFor(jwtProperties.refreshSecret.toByteArray(StandardCharsets.UTF_8))).subject
    }

    fun getTokenBody(token: String, secret: Key): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
