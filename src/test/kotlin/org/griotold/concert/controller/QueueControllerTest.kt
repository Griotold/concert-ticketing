package org.griotold.concert.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.griotold.concert.controller.dto.IssueTokenRequest
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
class QueueControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `issue should return hardcoded IssueTokenResponse`() {
        // given
        val request = IssueTokenRequest(memberId = 1L)
        val requestJson = objectMapper.writeValueAsString(request)

        // when
        val result = mockMvc.perform(
            post("/queue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )

        /**
         * 하드 코딩된 값 입니다.
         * */
        // then :
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.token").value("sampleToken123"))
            .andExpect(jsonPath("$.memberId").value("1"))
            .andExpect(jsonPath("$.waitingNum").value("42"))
    }
}