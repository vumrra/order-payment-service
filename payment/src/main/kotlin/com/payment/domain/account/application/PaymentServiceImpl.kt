package com.payment.domain.account.application

import com.payment.domain.account.persistence.AccountHistory
import com.payment.domain.account.persistence.AccountHistoryRepository
import com.payment.domain.account.persistence.AccountRepository
import com.payment.global.error.PaymentException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PaymentServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountHistoryRepository: AccountHistoryRepository,
) : PaymentService {

    @Transactional
    override fun pay(userId: Long, move: Long, depositDestination: String) {
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

    }

}
