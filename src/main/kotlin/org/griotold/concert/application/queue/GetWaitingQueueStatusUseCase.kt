package org.griotold.concert.application.queue

import org.griotold.concert.domain.queue.QueueService
import org.griotold.concert.domain.queue.WaitingQueue
import org.springframework.stereotype.Service

@Service
class GetWaitingQueueStatusUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(token: String): WaitingQueue {
        return queueService.getQueueStatus(token)
    }
}