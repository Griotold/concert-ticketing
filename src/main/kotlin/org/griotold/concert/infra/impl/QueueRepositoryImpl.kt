package org.griotold.concert.infra.impl

import org.griotold.concert.domain.entity.Queue
import org.griotold.concert.domain.repository.QueueRepository
import org.griotold.concert.infra.jpa.QueueJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class QueueRepositoryImpl(
    private val queueJpaRepository: QueueJpaRepository
) : QueueRepository {

    override fun findById(id: Long): Queue? {
        return queueJpaRepository.findByIdOrNull(id)
    }

    override fun save(queue: Queue): Queue {
        return queueJpaRepository.save(queue)
    }

    override fun delete(queue: Queue) {
        queueJpaRepository.delete(queue)
    }

}