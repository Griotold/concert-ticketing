package org.griotold.concert.application.queue

import org.griotold.concert.application.common.UseCase
import org.griotold.concert.domain.queue.QueueService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class ExitWaitingQueueUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(token: String) {
        queueService.exit(token)
    }
}