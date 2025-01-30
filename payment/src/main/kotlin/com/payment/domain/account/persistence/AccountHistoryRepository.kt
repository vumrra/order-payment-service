package com.payment.domain.account.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface AccountHistoryRepository : JpaRepository<AccountHistory, Long>