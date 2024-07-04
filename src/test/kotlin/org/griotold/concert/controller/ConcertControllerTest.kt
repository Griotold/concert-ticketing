package org.griotold.concert.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@AutoConfigureMockMvc
@SpringBootTest
class ConcertControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `getConcertSchedules should return hardcoded ConcertScheduleResponse`() {
        // given
        val concertId = 1L
        val token = "sampleToken123"


        /**
         * 토큰은 헤더에
         * */
        // when
        val result = mockMvc.perform(
            get("/concerts/$concertId")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
        )
        /**
         * 하드코딩된 값입니다.
         * */
        // then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.concertId").value(concertId))
            .andExpect(jsonPath("$.schedules[0].concertScheduleId").value("1"))
            .andExpect(jsonPath("$.schedules[0].concertStartAt").value("2024-07-01T20:00:00"))
            .andExpect(jsonPath("$.schedules[1].concertScheduleId").value("2"))
            .andExpect(jsonPath("$.schedules[1].concertStartAt").value("2024-07-02T20:00:00"))
    }

    @Test
    fun `getSeats should return hardcoded ConcertScheduleResponse`() {
        // given
        val concertId = 1L
        val concertScheduleId = 1L
        val token = "sampleToken123"


        /**
         * 토큰은 헤더에
         * */
        // when
        val result = mockMvc.perform(
            get("/concerts/$concertId/$concertScheduleId")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
        )
        /**
         * 하드코딩된 값입니다.
         * */
        // then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.concertScheduleId").value("1"))
            .andExpect(jsonPath("$.seats[0].seatId").value("1"))
            .andExpect(jsonPath("$.seats[0].seatNum").value("1"))
            .andExpect(jsonPath("$.seats[1].seatId").value("2"))
            .andExpect(jsonPath("$.seats[1].seatNum").value("2"))
    }
}