package com.product.global.lock

import java.util.concurrent.TimeUnit


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    /**
     * 락의 이름
     */
    val key: String,
    /**
     * 락의 시간 단위
     */
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    /**
     * 락 획득을 위해 기다리는 시간
     */
    val waitTime: Long = 5L,
    /**
     * 락 임대 시간
     */
    val leaseTime: Long = 3L
)
