package org.griotold.concert.infra.redis.queue

import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.QueueReader
import org.griotold.concert.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueReaderImpl (
    private val queueRedisRepository: QueueRedisRepository,
) : QueueReader {

    override fun getQueueStatus(token: String): WaitingQueue? {
        return queueRedisRepository.getRank(token)
            ?.let {
                WaitingQueue(
                    token = token,
                    rank = it.toInt(),
                    status = QueueStatus.WAITING,
                )
            }
            ?: if (queueRedisRepository.isProceedToken(token)) {
                return WaitingQueue(
                    token = token,
                    rank = 0,
                    status = QueueStatus.PROCEEDING,
                )
            } else {
                return null
            }
    }

    override fun getActiveCount(): Int {
        return queueRedisRepository.getActiveCount()
    }
}