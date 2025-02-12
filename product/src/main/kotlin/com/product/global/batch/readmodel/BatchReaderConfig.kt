package com.product.global.batch.readmodel

import com.product.domain.product.persistence.Product
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.persistence.EntityManagerFactory

@Configuration
class BatchReaderConfig(
    private val entityManagerFactory: EntityManagerFactory
) {
    @Bean
    fun productReader(): JpaPagingItemReader<Product> {
        return JpaPagingItemReader<Product>().apply {
            setEntityManagerFactory(entityManagerFactory)
            setQueryString("SELECT p FROM Product p")
            pageSize = 1000
        }
    }
}
