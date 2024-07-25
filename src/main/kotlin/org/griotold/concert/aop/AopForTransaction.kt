package org.griotold.concert.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * AOP에서 트랜잭션 분리를 위한 클래스
 */
@Component
class AopForTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Throws(Throwable::class)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }
}