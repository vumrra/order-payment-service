package com.product.global.config

import com.product.domain.product.persistence.Product
import com.product.domain.product.persistence.ProductDocument
import com.product.global.batch.readmodel.BatchProductProcessor
import com.product.global.batch.readmodel.BatchProductWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class BatchConfig(
    private val productReader: JpaPagingItemReader<Product>,
    private val productProcessor: BatchProductProcessor,
    private val productWriter: BatchProductWriter
) {

    @Bean
    fun productSyncJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Job {
        return JobBuilder("productSyncJob", jobRepository)
            .start(productSyncStep(jobRepository, transactionManager))
            .build()
    }

    @Bean
    fun productSyncStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("productSyncStep", jobRepository)
            .chunk<Product, ProductDocument>(1000)
            .transactionManager(transactionManager)
            .reader(productReader)
            .processor(productProcessor)
            .writer(productWriter)
            .build()
    }
}