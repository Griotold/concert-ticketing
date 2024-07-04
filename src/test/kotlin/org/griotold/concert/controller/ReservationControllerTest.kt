package org.griotold.concert.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.griotold.concert.controller.dto.ReservationRequest
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@AutoConfigureMockMvc
@SpringBootTest
class ReservationControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `reserve should return hardcoded ReservationResponse`() {
        // given
        val request = ReservationRequest(
            concertId = 1L,
            concertScheduleId = 1L,
            seatId = 1L,
            memberId = 1L
        )
        val requestJson = objectMapper.writeValueAsString(request)
        val token = "sampleToken123"

        // when
        val result = mockMvc.perform(
            post("/reservations")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )

        /**
         * 하드 코딩된 값입니다.
         * */
        // then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.reservationId").value("100"))
            .andExpect(jsonPath("$.concertInfo.concertId").value("1"))
            .andExpect(jsonPath("$.concertInfo.concertScheduleId").value("1"))
            .andExpect(jsonPath("$.concertInfo.name").value("Sample Concert"))
            .andExpect(jsonPath("$.concertInfo.concertStartAt").value("2024-07-01T20:00:00"))
            .andExpect(jsonPath("$.concertInfo.seatId").value("1"))
            .andExpect(jsonPath("$.concertInfo.seatNum").value("10"))
    }
}