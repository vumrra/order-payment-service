package com.payment.global.kafka.publisher

import com.payment.domain.account.event.PaymentFailedEvent
import com.payment.domain.account.event.PaymentSuccessEvent
import com.payment.global.kafka.properties.KafkaTopics.PAYMENT_FAILED
import com.payment.global.kafka.properties.KafkaTopics.PAYMENT_SUCCESS
import com.payment.global.publisher.TransactionEventPublisher
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentPublisher(
    private val transactionEventPublisher: TransactionEventPublisher
) {

    fun publishPaymentSuccessEvent(
        event: PaymentSuccessEvent
    ) {
        val key = UUID.randomUUID().toString()
        transactionEventPublisher.publishEvent(
            topic = PAYMENT_SUCCESS,
            key = key,
            event = event
        )
    }

    fun publishPaymentFailedEvent(
        event: PaymentFailedEvent
    ) {
        val key = UUID.randomUUID().toString()
        transactionEventPublisher.publishEvent(
            topic = PAYMENT_FAILED,
            key = key,
            event = event
        )
    }

}
