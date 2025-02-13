package com.product.domain.product.persistence
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoProductRepository : MongoRepository<ProductDocument, Long> {
    fun findByProductId(productId: Long): ProductDocument?
}
