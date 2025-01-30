package com.payment.domain.account.application

interface PaymentService {
    fun pay(userId: Long, orderId: Long,  move: Long, depositDestination: String)
}
