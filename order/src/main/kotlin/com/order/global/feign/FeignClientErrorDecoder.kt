package com.order.global.feign

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import com.order.global.error.OrderException
import org.springframework.http.HttpStatus

class FeignClientErrorDecoder : ErrorDecoder {
    override fun decode(
        methodKey: String?,
        response: Response,
    ): Exception? {
        if (response.status() >= 400) {
            throw OrderException("HTTP 통신 오류", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return FeignException.errorStatus(methodKey, response)
    }
}
