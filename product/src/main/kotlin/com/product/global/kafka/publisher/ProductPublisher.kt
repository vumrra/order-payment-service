package com.product.global.kafka.publisher

import com.product.domain.product.event.ProductReserveFailedEvent
import com.product.domain.product.event.ProductReservedEvent
import com.product.global.kafka.properties.KafkaTopics.PRODUCT_RESERVED
import com.product.global.kafka.properties.KafkaTopics.PRODUCT_RESERVE_FAILED
import com.product.global.publisher.TransactionEventPublisher
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProductPublisher(
    private val transactionEventPublisher: TransactionEventPublisher
) {

    fun publishProductReservedEvent(
        event: ProductReservedEvent,
    ) {
        val key = UUID.randomUUID().toString()
        transactionEventPublisher.publishEvent(
            topic = PRODUCT_RESERVED,
            key = key,
            event = event
        )
    }

    fun publishProductReserveFailedEvent(
        event: ProductReserveFailedEvent,
    ) {
        val key = UUID.randomUUID().toString()
        transactionEventPublisher.publishEvent(
            topic = PRODUCT_RESERVE_FAILED,
            key = key,
            event = event
        )
    }

}
