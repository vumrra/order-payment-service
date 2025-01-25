package com.gateway.token

import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.SignedJWT
import com.gateway.error.GatewayException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class Jwt {

    @Value("\${jwt.secret-key}")
    private val secretKey: String? = null

    fun parseToken(token: String): Map<String, Any> {
        val signedJwt = SignedJWT.parse(token)

        if(signedJwt.jwtClaimsSet.expirationTime.before(Date())) {
            throw GatewayException("Jwt is Expired", HttpStatus.UNAUTHORIZED)
        }

        val verifier = MACVerifier(secretKey)
        if(!signedJwt.verify(verifier)) {
            throw GatewayException("Invalid Jwt", HttpStatus.UNAUTHORIZED)
        }

        return signedJwt.jwtClaimsSet.toJSONObject()
    }

}
