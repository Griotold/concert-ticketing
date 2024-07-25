package org.griotold.concert.application.queue

import org.griotold.concert.application.common.UseCase
import org.griotold.concert.domain.queue.QueueService
import org.griotold.concert.domain.queue.WaitingQueue

@UseCase
class EnterWaitingQueueUseCase(
    private val queueService: QueueService
) {

    operator fun invoke(): WaitingQueue {
        return queueService.enter()
    }

}