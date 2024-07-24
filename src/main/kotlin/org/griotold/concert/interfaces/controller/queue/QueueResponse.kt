package org.griotold.concert.interfaces.controller.queue

import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.WaitingQueue

class QueueResponse {
    data class QueueInfo(
        val token: String,
        val rank: Int,
        val status: QueueStatus,
    ) {

        companion object {
            fun toResponse(waitingQueue: WaitingQueue): QueueInfo {
                return QueueInfo(
                    token = waitingQueue.token,
                    rank = waitingQueue.rank,
                    status = waitingQueue.status,
                )
            }
        }
    }
}