package com.payment.global.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.payment.domain.account.application.PaymentService
import com.payment.domain.account.event.PaymentFailedEvent
import com.payment.domain.account.event.PaymentSuccessEvent
import com.payment.global.kafka.consumer.dto.ProductReservedEvent
import com.payment.global.kafka.properties.KafkaTopics.PRODUCT_RESERVED
import com.payment.global.kafka.publisher.PaymentPublisher
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductReservedConsumer(
    private val objectMapper: ObjectMapper,
    private val paymentService: PaymentService,
    private val paymentPublisher: PaymentPublisher
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

        try {
            paymentService.pay(
                userId = event.userId,
                move = event.totalPrice,
                depositDestination = event.depositDestination,
            )
        } catch (e: Exception) {
            log.error("Failed to Payment, user id = ${event.userId}, depositDestination = ${event.depositDestination}", e)

            paymentPublisher.publishPaymentFailedEvent(
                PaymentFailedEvent(
                    id = UUID.randomUUID().toString(),
                    orderId = event.orderId
                )
            )
        }

        acknowledgment!!.acknowledge()
    }

}
