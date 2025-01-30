package com.payment.domain.account.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUserId(userId: Long): Account?
}