package com.product.global.batch.readmodel

import com.product.domain.product.persistence.Product
import com.product.domain.product.persistence.ProductDocument
import com.product.domain.product.persistence.SaleDocument
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class BatchProductProcessor: ItemProcessor<Product, ProductDocument> {
    override fun process(product: Product): ProductDocument {
        return ProductDocument(
            productId = product.id,
            categoryId = product.category.id,
            categoryName = product.category.name,
            name = product.name,
            originPrice = product.price,
            quantity = product.quantity,
            isSale = product.sale != null,
            orderCount = 0,
            totalOrderQuantity = 0,
            saleInfo = product.sale?.let {
                SaleDocument(
                    salePercentage = it.percentage,
                    salePrice = product.price * it.percentage / 100,
                    saleStartDate = it.startDate,
                    saleEndDate = it.endDate
                )
            },
            createdDate = product.createdDate
        )
    }
}
