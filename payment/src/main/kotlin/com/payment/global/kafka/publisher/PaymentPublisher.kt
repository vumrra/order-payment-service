package com.payment.global.kafka.publisher

import com.payment.global.publisher.TransactionEventPublisher
import org.springframework.stereotype.Component

@Component
class PaymentPublisher(
    private val transactionEventPublisher: TransactionEventPublisher
) {



}
