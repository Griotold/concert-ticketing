package org.griotold.concert.infra.redis.queue

import org.griotold.concert.domain.queue.QueueStore
import org.griotold.concert.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueStoreImpl (
    private val queueRedisRepository: QueueRedisRepository,
) : QueueStore {

    override fun enter(waitingQueue: WaitingQueue): WaitingQueue {
        val rank = queueRedisRepository.enterWaitQueue(waitingQueue.token)
        return waitingQueue.copy(rank = rank.toInt())
    }

    override fun exit(token: String) {
        queueRedisRepository.exitWaitQueue(token)
    }

    override fun clearExpiredToken() {
    }

    override fun activeTokens(targetCount: Int) {
        queueRedisRepository.activeTokens(targetCount.toLong())
    }
}