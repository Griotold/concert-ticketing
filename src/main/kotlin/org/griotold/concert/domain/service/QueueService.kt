package org.griotold.concert.domain.service

import org.griotold.concert.domain.repository.QueueRepository
import org.springframework.stereotype.Service
/**
 * 대기열 발급, 조회 기능
 * */
@Service
class QueueService (
    private val queueRepository: QueueRepository
){
}