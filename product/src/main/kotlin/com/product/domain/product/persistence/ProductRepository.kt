package com.product.domain.product.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCategoryId(categoryId: Long): List<Product>
}