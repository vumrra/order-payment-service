package com.product.global.batch.readmodel.scheduler

import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobParametersBuilder
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

@Component
class BatchScheduler(
    private val jobLauncher: JobLauncher,
    private val productSyncJob: Job
) {
    private val log = LoggerFactory.getLogger(javaClass)

    // 매일 새벽 3시에 전체 Product Read Model Full Batch Sync
    @Scheduled(cron = "30 59 18 * * *", zone = "Asia/Seoul")
    fun runBatchJob() {
        log.info("[Batch] 상품 동기화 시작: ${LocalDateTime.now()}")

        val jobParameters = JobParametersBuilder()
            .addString("timestamp", System.currentTimeMillis().toString())
            .toJobParameters()

        val executionTime = measureTimeMillis {
            val jobExecution = jobLauncher.run(productSyncJob, jobParameters)
            log.info("[Batch] 상품 동기화 상태: ${jobExecution.status}")
        }

        log.info("[Batch] 상품 동기화 완료, 총 실행 시간: ${executionTime}ms")
    }
}
