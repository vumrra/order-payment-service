package com.product.global.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAop(
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AopForTransaction
) {

    companion object {
        const val REDISSON_LOCK_PREFIX = "LOCK:"
    }

    private val log = LoggerFactory.getLogger(this.javaClass.name)
    @Around("@annotation(com.product.global.lock.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock = method.getAnnotation(DistributedLock::class.java)

        val rawKey: String = DistributedLockKeyGenerator.generate(
            parameterNames = signature.parameterNames,
            args = joinPoint.args,
            key = distributedLock.key
        ).toString()

        val keys: List<String> = if (rawKey.contains("reserve_product_")) {
            rawKey.removePrefix("reserve_product_")
                .removeSurrounding("[", "]")
                .split(",")
                .map { "reserve_product_$it" }
        } else {
            listOf(rawKey)
        }.map { REDISSON_LOCK_PREFIX + it }

        val locks = keys.map { redissonClient.getLock(it) }

        log.info("lock on method: $method keys: $keys")

        try {
            val available = locks.all { it.tryLock(distributedLock.waitTime, distributedLock.leaseTime, distributedLock.timeUnit) }

            if (!available) {
                throw RuntimeException("락을 취득할 수 없습니다.")
            }

            return aopForTransaction.proceed(joinPoint)

        } catch (e: InterruptedException) {
            throw RuntimeException("락 인터럽트 발생")
        } finally {
            locks.forEach {
                try {
                    it.unlock()
                    log.info("unlock complete [Lock: ${it.name}]")
                } catch (e: IllegalMonitorStateException) {
                    log.info("Redisson Lock Already Unlocked")
                }
            }
        }
    }

}
