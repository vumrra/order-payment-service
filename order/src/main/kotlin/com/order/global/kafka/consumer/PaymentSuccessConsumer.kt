package com.order.global.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.order.domain.order.application.OrderService
import com.order.domain.order.event.PaymentSuccessEvent
import com.order.global.kafka.properties.KafkaTopics.PAYMENT_SUCCESS
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class PaymentSuccessConsumer(
    private val objectMapper: ObjectMapper,
    private val orderService: OrderService,
) : AcknowledgingMessageListener<String, String> {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @KafkaListener(
        topics = [PAYMENT_SUCCESS],
        groupId = "op",
        containerFactory = "paymentSuccessEventListenerContainerFactory"
    )
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), PaymentSuccessEvent::class.java)
        log.info("${PAYMENT_SUCCESS}Topic, key: $key, event: $event")

        orderService.confirm(event.orderId)

        acknowledgment!!.acknowledge()
    }

}
