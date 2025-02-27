package com.gateway.error

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import java.net.ConnectException

@Component
class ErrorExchangeHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    webProperties.resources,
    applicationContext
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), this::handleError)
    }

    private fun handleError(request: ServerRequest): Mono<ServerResponse> =
        when(val throwable = super.getError(request)) {
            is GatewayException -> buildErrorResponse(throwable)
            is ConnectException -> buildErrorResponse(GatewayException("Cannot Connect to Service", HttpStatus.INTERNAL_SERVER_ERROR))
            else -> buildErrorResponse(GatewayException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR))
        }

    private fun buildErrorResponse(ex: GatewayException): Mono<ServerResponse> {
        log.error(ex.message)
        return ServerResponse.status(ex.httpStatus)
            .bodyValue(
                ErrorResponse.of(ex.message ?: "Not Exists Cause by Exception")
            )
    }

}