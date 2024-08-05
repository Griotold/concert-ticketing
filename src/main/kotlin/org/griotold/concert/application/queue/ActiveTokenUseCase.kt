package org.griotold.concert.application.queue

import org.griotold.concert.application.common.UseCase
import org.griotold.concert.domain.queue.QueueService

@UseCase
class ActiveTokenUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(maxUserCount: Int) {
        queueService.clearExpiredToken()
        queueService.activeTokens(maxUserCount)
    }
}