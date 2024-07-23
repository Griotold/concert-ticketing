package org.griotold.concert.application.queue

import org.griotold.concert.domain.queue.QueueService
import org.springframework.stereotype.Service

@Service
class ActiveTokenUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(maxUserCount: Int) {
        queueService.clearExpiredToken()
        queueService.activeTokens(maxUserCount)
    }
}