package com.product.global.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.product.domain.product.application.ProductService
import com.product.global.kafka.consumer.dto.PaymentFailedEvent
import com.product.global.kafka.properties.KafkaTopics.PAYMENT_FAILED
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class PaymentFailedConsumer(
    private val objectMapper: ObjectMapper,
    private val productService: ProductService
) : AcknowledgingMessageListener<String, String> {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @KafkaListener(
        topics = [PAYMENT_FAILED],
        groupId = "op-product",
        containerFactory = "paymentFailedEventListenerContainerFactory"
    )
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), PaymentFailedEvent::class.java)
        log.info("${PAYMENT_FAILED}Topic, key: $key, event: $event")

        productService.rollbackProduct(event)

        acknowledgment!!.acknowledge()
    }

}