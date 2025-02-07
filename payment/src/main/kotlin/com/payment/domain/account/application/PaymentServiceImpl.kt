package com.payment.domain.account.application

import com.payment.domain.account.event.PaymentSuccessEvent
import com.payment.domain.account.persistence.AccountHistory
import com.payment.domain.account.persistence.AccountHistoryRepository
import com.payment.domain.account.persistence.AccountRepository
import com.payment.global.error.PaymentException
import com.payment.global.lock.DistributedLock
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class PaymentServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountHistoryRepository: AccountHistoryRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : PaymentService {

    @DistributedLock(key = "'pay_' + #userId")
    @Transactional
    override fun pay(userId: Long, orderId: Long, move: Long, depositDestination: String) {
        val account = (accountRepository.findByUserId(userId)
            ?: throw PaymentException("Account with id $userId does not exist.", HttpStatus.NOT_FOUND))

        val balanceAfterTransaction = account.balance - move
        account.minus(move)

        val accountHistory = AccountHistory(
            account = account,
            transactionAmount = move,
            balanceAfterTransaction = balanceAfterTransaction,
            depositDestination = depositDestination,
            date = LocalDateTime.now(),
        )

        accountRepository.save(account)
        accountHistoryRepository.save(accountHistory)

        applicationEventPublisher.publishEvent(
            PaymentSuccessEvent(
                id = UUID.randomUUID().toString(),
                orderId = orderId
            )
        )

    }
}
