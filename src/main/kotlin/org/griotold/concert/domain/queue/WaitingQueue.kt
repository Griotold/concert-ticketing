package org.griotold.concert.domain.queue

import org.griotold.concert.domain.common.type.QueueStatus
import java.time.LocalDateTime

data class WaitingQueue(
    val queueId: Long = 0,
    val token: String,
    val rank: Int,
    val status: QueueStatus,
    val expiredAt: LocalDateTime? = null
) {

    companion object {
        fun enter(token: String): WaitingQueue {
            return WaitingQueue(
                token = token,
                rank = 0,
                status = QueueStatus.WAITING,
            )
        }
    }
}