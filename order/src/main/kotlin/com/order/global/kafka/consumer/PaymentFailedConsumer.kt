package com.order.global.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.order.domain.order.application.OrderService
import com.order.domain.order.event.PaymentFailedEvent
import com.order.domain.order.persistence.OrderCancelReason
import com.order.global.kafka.properties.KafkaTopics.PAYMENT_FAILED
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class PaymentFailedConsumer(
    private val objectMapper: ObjectMapper,
    private val orderService: OrderService,
) : AcknowledgingMessageListener<String, String> {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @KafkaListener(
        topics = [PAYMENT_FAILED],
        groupId = "op",
        containerFactory = "paymentFailedEventListenerContainerFactory"
    )
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), PaymentFailedEvent::class.java)
        log.info("${PAYMENT_FAILED}Topic, key: $key, event: $event")

        orderService.cancel(event.orderId, OrderCancelReason.PAYMENT_FAILED)

        acknowledgment!!.acknowledge()
    }

}
