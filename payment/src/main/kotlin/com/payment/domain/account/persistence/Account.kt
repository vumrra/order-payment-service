package com.payment.domain.account.persistence

import com.payment.global.error.PaymentException
import jakarta.persistence.*
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_account")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long,
    var balance: Long,
    val createdDate: LocalDateTime = LocalDateTime.now(),
) {

    fun minus(move: Long) {
        if (balance - move >= 0) {
            balance -= move
        } else {
            throw PaymentException("잔액부족", HttpStatus.BAD_REQUEST)
        }
    }

    fun plus(move: Long) {
        balance += move
    }

    companion object {
        fun of(userId: Long) =
            Account(
                userId = userId,
                balance = 0,
                createdDate = LocalDateTime.now()
            )
    }
}