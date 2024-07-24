package org.griotold.concert.interfaces.controller.reservation

import org.griotold.concert.application.reservation.PerformanceSeatReservationUseCase
import org.griotold.concert.application.reservation.ReservationPaymentUseCase
import org.griotold.concert.interfaces.controller.WebTestSupport
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ReservationController::class)
class ReservationControllerTest : WebTestSupport() {

    @MockBean
    private lateinit var performanceSeatReservationUseCase: PerformanceSeatReservationUseCase

    @MockBean
    private lateinit var reservationPaymentUseCase: ReservationPaymentUseCase

    @Test
    fun `좌석 예약 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val request = ReservationRequest.ReserveSeat(
            userId = 1L,
            seatId = 1L,
        )

        // then
        mockMvc.perform(
            post("/api/reservations")
                .header("wq-token", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `좌석 예약 시 userId 는 양수이다`(userId: Long) {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val request = ReservationRequest.ReserveSeat(
            userId = userId,
            seatId = 1L,
        )

        // then
        mockMvc.perform(
            post("/api/reservations")
                .header("wq-token", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("userId는 양수 값 이어야 합니다."))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `좌석 예약 시 seatId 는 양수이다`(seatId: Long) {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val request = ReservationRequest.ReserveSeat(
            userId = 1L,
            seatId = seatId,
        )

        // then
        mockMvc.perform(
            post("/api/reservations")
                .header("wq-token", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("seatId는 양수 값 이어야 합니다."))
    }

    @Test
    fun `예약된 좌석 결제 성공`() {
        // given
        val reservationId = 1L
        val request = ReservationRequest.Payment(
            userId = 1L,
        )

        // then
        mockMvc.perform(
            post("/api/reservations/%d/payments".format(reservationId))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `예약된 좌석 결제 시 reservationId 는 양수이다`(reservationId: Long) {
        // given
        val request = ReservationRequest.Payment(
            userId = 1L,
        )

        // then
        mockMvc.perform(
            post("/api/reservations/%d/payments".format(reservationId))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("reservationId는 양수 값 이어야 합니다."))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `예약된 좌석 결제 시 userId 는 양수이다`(userId: Long) {
        // given
        val reservationId = 1L
        val request = ReservationRequest.Payment(
            userId = userId,
        )

        // then
        mockMvc.perform(
            post("/api/reservations/%d/payments".format(reservationId))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("userId는 양수 값 이어야 합니다."))
    }
}