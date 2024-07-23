package org.griotold.concert.interfaces.controller.user

import org.griotold.concert.application.user.ChargeUserPointUseCase
import org.griotold.concert.application.user.GetUserPointUseCase
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.griotold.concert.interfaces.controller.WebTestSupport
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserPointController::class)
class UserPointControllerTest() : WebTestSupport() {

    @MockBean
    lateinit var getUserPointUseCase: GetUserPointUseCase

    @MockBean
    lateinit var chargeUserPointUseCase: ChargeUserPointUseCase

    @Test
    fun `유저 포인트 조회 성공`() {
        // given
        val userId = 1L
        given(getUserPointUseCase(UserCommand.GetPoint(userId)))
            .willReturn(User(userId, "유저1", UserPoint(0)))

        // then
        mockMvc.perform(
            get("/api/users/%d/point".format(userId))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `유저 포인트 조회 시 userId 는 양수이다`(userId: Long) {
        // given

        // then
        mockMvc.perform(
            get("/api/users/%d/point".format(userId))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("userId는 양수 값 이어야 합니다."))
    }

    @Test
    fun `유저 포인트 충전 성공`() {
        // given
        val userId = 1L
        val amount = 10000
        val request = UserRequest.ChargePoint(amount)

        // then
        mockMvc.perform(
            put("/api/users/%d/point".format(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `유저 포인트 충전 시 userId 는 양수이다`(userId: Long) {
        // given
        val amount = 10000
        val request = UserRequest.ChargePoint(amount)

        // then
        mockMvc.perform(
            put("/api/users/%d/point".format(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("userId는 양수 값 이어야 합니다."))
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0])
    fun `유저 포인트 충전 시 amount 는 양수이다`(amount: Int) {
        // given
        val userId = 1L
        val request = UserRequest.ChargePoint(amount)

        // then
        mockMvc.perform(
            put("/api/users/%d/point".format(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("amount는 양수 값 이어야 합니다."))
    }
}
