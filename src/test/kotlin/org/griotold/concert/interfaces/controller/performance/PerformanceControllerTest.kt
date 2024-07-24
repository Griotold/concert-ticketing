package org.griotold.concert.interfaces.controller.performance

import org.griotold.concert.application.performance.GetAvailableSeatListUseCase
import org.griotold.concert.application.performance.GetPerformanceListUseCase
import org.griotold.concert.application.performance.GetPerformanceScheduleListUseCase
import org.griotold.concert.domain.common.PageInfo
import org.griotold.concert.domain.common.WithPage
import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.domain.performance.Performance
import org.griotold.concert.domain.performance.PerformanceSchedule
import org.griotold.concert.domain.performance.Seat
import org.griotold.concert.interfaces.controller.WebTestSupport
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(PerformanceController::class)
class PerformanceControllerTest : WebTestSupport() {

    @MockBean
    lateinit var getPerformanceListUseCase: GetPerformanceListUseCase

    @MockBean
    lateinit var getPerformanceScheduleListUseCase: GetPerformanceScheduleListUseCase

    @MockBean
    lateinit var getAvailableSeatListUseCase: GetAvailableSeatListUseCase

    @Test
    fun `공연 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val pageNo = 1
        val pageSize = 2
        val pageInfo = PageInfo(pageNo, pageSize, 2)

        given(getPerformanceListUseCase(any()))
            .willReturn(WithPage(listOf(), pageInfo))

        // then
        mockMvc.perform(
            get("/api/performances")
                .param("pageNo", pageNo.toString())
                .param("pageSize", pageSize.toString())
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.performances").isArray)
    }

    @Test
    fun `공연 스케줄 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val performanceId = 1L
        val performance = Performance(
            performanceId,
            "공연1",
            "내용1",
            listOf(
                PerformanceSchedule(
                    performanceScheduleId = 1L,
                    startAt = LocalDateTime.of(2024, 4, 12, 17, 0, 0),
                    endAt = LocalDateTime.of(2024, 4, 12, 20, 0, 0),
                    reservationAt = LocalDateTime.of(2024, 4, 1, 17, 0, 0),
                )
            )
        )

        given(getPerformanceScheduleListUseCase(performanceId))
            .willReturn(performance)

        // then
        mockMvc.perform(
            get("/api/performances/%d".format(performanceId))
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.title").value("공연1"))
            .andExpect(jsonPath("$.body.content").value("내용1"))
            .andExpect(jsonPath("$.body.date").isArray)
            .andExpect(jsonPath("$.body.date[0].performanceScheduleId").value("1"))
            .andExpect(jsonPath("$.body.date[0].reservationAt").value("2024-04-01T17:00:00"))
            .andExpect(jsonPath("$.body.date[0].startAt").value("2024-04-12T17:00:00"))
            .andExpect(jsonPath("$.body.date[0].endAt").value("2024-04-12T20:00:00"))
    }

    @Test
    fun `예약 가능한 좌석 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val performanceScheduleId = 1L
        val expected = listOf(
            Seat(
                seatId = 1L,
                performanceScheduleId = 1L,
                seatNo = 1,
                price = 10000,
                SeatStatus.OPEN
            ),
        )

        given(getAvailableSeatListUseCase(performanceScheduleId))
            .willReturn(expected)

        // then
        mockMvc.perform(
            get("/api/performances/%d/seats".format(performanceScheduleId))
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.availableSeats").isArray)
            .andExpect(jsonPath("$.body.availableSeats[0].seatId").value("1"))
            .andExpect(jsonPath("$.body.availableSeats[0].seatNo").value("1"))
            .andExpect(jsonPath("$.body.availableSeats[0].price").value("10000"))
    }
}