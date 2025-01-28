package com.order.global.kafka.publisher

import com.order.domain.order.event.OrderReservedEvent
import com.order.global.kafka.properties.KafkaTopics.ORDER_RESERVED
import com.order.global.publisher.TransactionEventPublisher
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderPublisher(
    private val transactionEventPublisher: TransactionEventPublisher
) {

    fun publishOrderReservedEvent(
        orderReservedEvent: OrderReservedEvent
    ) {
        val key = UUID.randomUUID().toString()
        transactionEventPublisher.publishEvent(
            topic = ORDER_RESERVED,
            key = key,
            event = orderReservedEvent
        )
    }

}
