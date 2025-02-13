package com.product.domain.product.application

import com.product.domain.product.persistence.*
import com.product.global.error.ProductException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductQueryServiceImpl(
    private val mongoProductRepository: MongoProductRepository,
) : ProductQueryService {

    @Transactional(readOnly = true)
    override fun queryProduct(productId: Long): ProductDocument {
        return mongoProductRepository.findById(productId)
            .orElseThrow { throw ProductException("Product with id ${productId} not found", HttpStatus.NOT_FOUND) }
    }

}
