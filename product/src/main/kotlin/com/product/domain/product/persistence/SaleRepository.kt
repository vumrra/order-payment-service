package com.product.domain.product.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SaleRepository: JpaRepository<Sale, Long>
