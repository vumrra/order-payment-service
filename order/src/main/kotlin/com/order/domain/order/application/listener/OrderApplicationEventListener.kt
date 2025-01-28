package com.order.domain.order.application.listener

import com.order.domain.order.event.OrderReservedEvent
import com.order.global.kafka.publisher.OrderPublisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderApplicationEventListener(
    private val orderPublisher: OrderPublisher,
) {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun orderReservedEvent(event: OrderReservedEvent) {
        with(event) {
            log.info("published order reserved application event: {}", id)
            orderPublisher.publishOrderReservedEvent(event)
        }
    }

}
