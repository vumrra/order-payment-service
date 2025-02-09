package com.payment.global.lock

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

    @Around("@annotation(com.payment.global.lock.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock = method.getAnnotation(DistributedLock::class.java)

        val key = REDISSON_LOCK_PREFIX + DistributedLockKeyGenerator.generate(
            parameterNames = signature.parameterNames,
            args = joinPoint.args,
            key = distributedLock.key
        )
        log.info("lock on method: $method key: $key", method, key)

        val rLock = redissonClient.getLock(key)
        val lockName = rLock.name

        try {
            val available = rLock.tryLock(
                distributedLock.waitTime,
                distributedLock.leaseTime,
                distributedLock.timeUnit
            )

            if(!available) {
                throw RuntimeException("락을 취득할 수 없습니다.")
            }

            return aopForTransaction.proceed(joinPoint)

        } catch (e: InterruptedException) {
            throw RuntimeException("락 인터럽트 발생")
        } finally {
            try {
                rLock.unlock()
                log.info("unlock complete [Lock: $lockName]")
            } catch (e: IllegalMonitorStateException) {
                log.info("Redisson Lock Already Unlocked")
            }
        }
    }

}
