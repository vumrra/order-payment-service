package com.payment.domain.account.persistence

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_account_history")
class AccountHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val account: Account,
    val transactionAmount: Long,
    val balanceAfterTransaction: Long,
    val depositDestination: String,
    val date: LocalDateTime,
)
