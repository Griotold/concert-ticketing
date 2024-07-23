package org.griotold.concert.interfaces.controller.queue

import org.griotold.concert.application.queue.EnterWaitingQueueUseCase
import org.griotold.concert.application.queue.ExitWaitingQueueUseCase
import org.griotold.concert.application.queue.GetWaitingQueueStatusUseCase
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.WaitingQueue
import org.griotold.concert.interfaces.controller.WebTestSupport
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QueueController::class)
class QueueControllerTest : WebTestSupport() {

    @MockBean
    lateinit var enterWaitingQueueUseCase: EnterWaitingQueueUseCase

    @MockBean
    lateinit var getWaitingQueueStatusUseCase: GetWaitingQueueStatusUseCase

    @MockBean
    lateinit var exitWaitingQueueUseCase: ExitWaitingQueueUseCase

    @Test
    fun `대기열 입장 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val expected = WaitingQueue(
            queueId = 1L,
            token = token,
            rank = 10,
            status = QueueStatus.WAITING,
            expiredAt = null
        )

        given(enterWaitingQueueUseCase()).willReturn(expected)

        // then
        mockMvc.perform(
            post("/api/queue/enter")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.token").value("4844c369-717f-4730-8b4f-c3a890094daa"))
            .andExpect(jsonPath("$.body.rank").value(10))
            .andExpect(jsonPath("$.body.status").value("WAITING"))
    }

    @Test
    fun `대기열 상태 조회`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val expected = WaitingQueue(
            queueId = 1L,
            token = token,
            rank = 10,
            status = QueueStatus.WAITING,
            expiredAt = null
        )

        given(getWaitingQueueStatusUseCase(token)).willReturn(expected)

        // then
        mockMvc.perform(
            get("/api/queue/status")
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.token").value("4844c369-717f-4730-8b4f-c3a890094daa"))
            .andExpect(jsonPath("$.body.rank").value(10))
            .andExpect(jsonPath("$.body.status").value("WAITING"))
    }

    @Test
    fun `대기열 퇴장`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"

        // then
        mockMvc.perform(
            delete("/api/queue/exit")
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }
}