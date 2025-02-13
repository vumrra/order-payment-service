package com.product.domain.product.application

import com.product.domain.product.persistence.ProductDocument

interface ProductQueryService {
    fun queryProduct(productId: Long): ProductDocument
}
