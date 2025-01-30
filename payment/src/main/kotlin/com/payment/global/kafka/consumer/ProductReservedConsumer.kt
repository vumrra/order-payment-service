package com.payment.global.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.payment.global.kafka.consumer.dto.ProductReservedEvent
import com.payment.global.kafka.properties.KafkaTopics.PRODUCT_RESERVED
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class ProductReservedConsumer(
    private val objectMapper: ObjectMapper,
) : AcknowledgingMessageListener<String, String> {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @KafkaListener(
        topics = [PRODUCT_RESERVED],
        groupId = "op",
        containerFactory = "productReservedEventListenerContainerFactory"
    )
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), ProductReservedEvent::class.java)
        log.info("${PRODUCT_RESERVED}Topic, key: $key, event: $event")

        acknowledgment!!.acknowledge()
    }

}
