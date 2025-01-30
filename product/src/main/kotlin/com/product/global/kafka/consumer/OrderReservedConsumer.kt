package com.product.global.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.product.domain.product.application.ProductService
import com.product.global.kafka.consumer.dto.OrderReservedEvent
import com.product.domain.product.event.ProductReserveFailedEvent
import com.product.global.kafka.properties.KafkaTopics.ORDER_RESERVED
import com.product.global.kafka.publisher.ProductPublisher
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderReservedConsumer(
    private val objectMapper: ObjectMapper,
    private val productPublisher: ProductPublisher,
    private val productService: ProductService
) : AcknowledgingMessageListener<String, String> {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @KafkaListener(
        topics = [ORDER_RESERVED],
        groupId = "op",
        containerFactory = "orderReservedEventListenerContainerFactory"
    )
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), OrderReservedEvent::class.java)
        log.info("${ORDER_RESERVED}Topic, key: $key, event: $event")

        try {
            productService.deductedProduct(event)
        } catch (_: Exception) {
            log.error("Failed to Reserve Product order id = ${event.orderId}, user id = ${event.userId}")

            productPublisher.publishProductReserveFailedEvent(
                ProductReserveFailedEvent(
                    id = UUID.randomUUID().toString(),
                    userId = event.userId,
                    orderId = event.orderId,
                )
            )
        }

        acknowledgment!!.acknowledge()
    }

}