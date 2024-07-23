package org.griotold.concert.domain.queue

import org.griotold.concert.domain.queue.QueueResponseCode.QUEUE_TOKEN_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class QueueService(
    private val queueReader: QueueReader,
    private val queueStore: QueueStore,
    private val tokenGenerator: TokenGenerator,
) {

    fun enter(): WaitingQueue {
        val token = tokenGenerator.generate()
        val waitingQueue = WaitingQueue.enter(token)
        return queueStore.enter(waitingQueue)
    }

    fun getQueueStatus(token: String): WaitingQueue {
        return queueReader.getQueueStatus(token)
            ?: throw QueueException(QUEUE_TOKEN_NOT_FOUND)
    }

    fun exit(token: String) {
        queueStore.exit(token)
    }

    fun clearExpiredToken() {
        queueStore.clearExpiredToken()
    }

    fun activeTokens(maxUserCount: Int) {
        val activeCount = queueReader.getActiveCount()
        val targetCount = maxUserCount - activeCount
        queueStore.activeTokens(targetCount)
    }
}