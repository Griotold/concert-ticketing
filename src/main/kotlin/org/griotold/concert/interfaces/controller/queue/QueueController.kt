package org.griotold.concert.interfaces.controller.queue

import org.griotold.concert.application.queue.EnterWaitingQueueUseCase
import org.griotold.concert.application.queue.ExitWaitingQueueUseCase
import org.griotold.concert.application.queue.GetWaitingQueueStatusUseCase
import org.griotold.concert.interfaces.controller.CommonResponse
import org.griotold.concert.interfaces.controller.common.QueueToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/queue")
class QueueController(
    private val enterWaitingQueueUseCase: EnterWaitingQueueUseCase,
    private val getWaitingQueueStatusUseCase: GetWaitingQueueStatusUseCase,
    private val exitWaitingQueueUseCase: ExitWaitingQueueUseCase,
) {

    @PostMapping("/enter")
    fun enterWaitingQueue(): CommonResponse<QueueResponse.QueueInfo> {
        val result = enterWaitingQueueUseCase()
        return CommonResponse.ok(
            QueueResponse.QueueInfo.toResponse(result)
        )
    }

    @GetMapping("/status")
    fun checkWaitingQueue(
        @QueueToken token: String,
    ): CommonResponse<QueueResponse.QueueInfo> {
        val result = getWaitingQueueStatusUseCase(token)
        return CommonResponse.ok(
            QueueResponse.QueueInfo.toResponse(result)
        )
    }

    @DeleteMapping("/exit")
    fun exitWaitingQueue(
        @QueueToken token: String,
    ): CommonResponse<Void> {
        exitWaitingQueueUseCase(token)
        return CommonResponse.ok()
    }
}