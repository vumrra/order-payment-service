package com.product.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
class ReadModelAsyncConfig {
    @Bean(name = ["readModelTaskExecutor"])
    fun readModelTaskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 100
        executor.setThreadNamePrefix("[ASYNC] -")
        executor.initialize()
        return executor
    }
}
