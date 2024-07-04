package org.griotold.concert.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.griotold.concert.controller.dto.PayRequest
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@AutoConfigureMockMvc
@SpringBootTest
class PaymentControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `pay should return hardcoded ReservationResponse`() {
        // given
        val request = PayRequest(
            memberId = 1L
        )
        val requestJson = objectMapper.writeValueAsString(request)
        val token = "sampleToken123"
        val reservationId = 200L

        // when
        val result = mockMvc.perform(
            post("/payments/$reservationId")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )

        // then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.paymentId").value("200"))
            .andExpect(jsonPath("$.paymentPrice").value("5000"))
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.paidAt").value("2024-07-01T20:00:00"))
    }
}