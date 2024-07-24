package org.griotold.concert.interfaces.scheduler.queue

import org.griotold.concert.application.queue.ActiveTokenUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QueueScheduler(
    private val activeTokenUseCase: ActiveTokenUseCase,
) {
    private val log: Logger get() = LoggerFactory.getLogger(this::class.java)
    private val MAX_USER_COUNT = 50

    @Scheduled(fixedDelay = 5000)
    fun activeToken() {
        activeTokenUseCase(MAX_USER_COUNT)
    }
}