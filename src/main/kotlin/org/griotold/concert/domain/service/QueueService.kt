package org.griotold.concert.domain.service

import jakarta.persistence.EntityNotFoundException
import org.griotold.concert.domain.component.TokenGenerator
import org.griotold.concert.domain.entity.Member
import org.griotold.concert.domain.entity.Queue
import org.griotold.concert.domain.repository.QueueRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * 대기열 발급, 조회 기능
 * */
@Service
class QueueService (
    private val queueRepository: QueueRepository,
    private val tokenGenerator: TokenGenerator,
){
    fun issue(member: Member): Queue {
        val token = tokenGenerator.generateToken(member.id!!)
        return queueRepository.save(Queue(member = member, token = token))
    }

    fun updateWaitingNo(queue: Queue) : Queue{
        val lastTokenId = queueRepository.getLastTokenId(queue.id!!)
        val waitingNo = queue.id!! - lastTokenId
        queue.updateWaitingNo(waitingNo = waitingNo.toInt())
        return queue
    }

    fun retrieve(token: String): Queue {
        return queueRepository.findByToken(token) ?: throw EntityNotFoundException("Not Found")
    }
}