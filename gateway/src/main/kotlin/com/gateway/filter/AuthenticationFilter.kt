package com.gateway.filter

import com.gateway.token.Jwt
import com.gateway.token.JwtParser.getAuthorizationFromHeader
import com.gateway.token.JwtParser.removeJwtTokenPrefix
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*

@Component
class AuthenticationFilter(
    private val jwt: Jwt
) : GlobalFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val token = request.headers.getAuthorizationFromHeader()
        val requestBuilder = request.mutate()

        requestBuilder.header("Request-Id", UUID.randomUUID().toString())
        token?.let {
            val pureToken = it.removeJwtTokenPrefix()
            val jwtTokenClaims = jwt.parseToken(pureToken)

            val subject = jwtTokenClaims["sub"].toString()
            val authority = jwtTokenClaims["authority"].toString()

            requestBuilder.header("Request-User-Id", subject)
            requestBuilder.header("Request-User-Authority", authority)
        }

        val modifiedRequest = requestBuilder.build()

        val modifiedExchange = exchange.mutate()
            .request(modifiedRequest)
            .build()

        return chain.filter(modifiedExchange)
    }

}