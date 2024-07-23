package org.griotold.concert.application.queue

import org.griotold.concert.application.UseCase
import org.griotold.concert.domain.queue.QueueService
import org.griotold.concert.domain.queue.WaitingQueue

@UseCase
class GetWaitingQueueStatusUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(token: String): WaitingQueue {
        return queueService.getQueueStatus(token)
    }
}