package com.product.domain.product.application.listener

import com.product.domain.product.application.ProductProcessor
import com.product.domain.product.event.ProductCreatedEvent
import com.product.domain.product.event.ProductReservedEvent
import com.product.global.kafka.publisher.ProductPublisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ProductApplicationEventListener(
    private val productPublisher: ProductPublisher,
    private val productProcessor: ProductProcessor
) {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun productReservedEvent(event: ProductReservedEvent) {
        with(event) {
            log.info("published order reserved application event: {}", id)
            productPublisher.publishProductReservedEvent(event)
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun productCreatedEvent(event: ProductCreatedEvent) {
        log.info("published product created application event: {}", event)
        productProcessor.createProductReadModel(event)
    }

}
