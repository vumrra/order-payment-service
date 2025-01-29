package com.product.global.kafka.publisher

import com.product.global.publisher.TransactionEventPublisher
import org.springframework.stereotype.Component

@Component
class ProductPublisher(
    private val transactionEventPublisher: TransactionEventPublisher
) {

}
