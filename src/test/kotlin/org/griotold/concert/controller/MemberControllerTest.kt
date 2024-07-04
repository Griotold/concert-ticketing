package org.griotold.concert.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.griotold.concert.controller.dto.ChargeRequest
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `charge should return hardcoded ChargeResponse`() {
        // given
        val request = ChargeRequest(amount = 1000L)
        val requestJson = objectMapper.writeValueAsString(request)
        val memberId = 1L

        // when
        val result = mockMvc.perform(
            put("/members/charge/$memberId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )

        // then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.memberId").value(memberId))
            .andExpect(jsonPath("$.transactionType").value("CHARGE"))
            .andExpect(jsonPath("$.amount").value(1000L))
    }

    @Test
    fun `getPoints should return hardcoded ChargeResponse`() {
        // given
        val memberId = 1L

        // when
        val result = mockMvc.perform(
            get("/members/points/$memberId")
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.memberId").value(memberId))
            .andExpect(jsonPath("$.points").value("5000"))
    }
}