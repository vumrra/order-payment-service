package com.payment.domain.account.application.listener

import com.payment.domain.account.event.PaymentSuccessEvent
import com.payment.global.kafka.publisher.PaymentPublisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PaymentApplicationEventListener(
    private val paymentPublisher: PaymentPublisher,
) {

    private val log = LoggerFactory.getLogger(this::class.java.simpleName)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun paymentSuccessEvent(event: PaymentSuccessEvent) {
        with(event) {
            log.info("published payment success application event: {}", id)
            paymentPublisher.publishPaymentSuccessEvent(event)
        }
    }

}
