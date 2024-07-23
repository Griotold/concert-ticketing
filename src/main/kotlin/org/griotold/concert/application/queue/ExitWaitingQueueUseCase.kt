package org.griotold.concert.application.queue

import org.griotold.concert.domain.queue.QueueService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ExitWaitingQueueUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(token: String) {
        queueService.exit(token)
    }
}