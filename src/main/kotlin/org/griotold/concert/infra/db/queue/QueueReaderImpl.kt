package org.griotold.concert.infra.db.queue

import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.QueueReader
import org.griotold.concert.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueReaderImpl(
    private val queueJpaRepository: QueueJpaRepository,
) : QueueReader {

    override fun getQueueStatus(token: String): WaitingQueue? {
        return queueJpaRepository.findByToken(token)
            ?.let {
                val rank = queueJpaRepository.rank(it.id!!)
                it.toDomain(rank)
            }
    }

    override fun getActiveCount(): Int {
        return queueJpaRepository.countByStatus(QueueStatus.PROCEEDING)
    }
}