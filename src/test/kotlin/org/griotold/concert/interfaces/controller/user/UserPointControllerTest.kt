package org.griotold.concert.interfaces.controller.user

import org.griotold.concert.application.user.ChargeUserPointUseCase
import org.griotold.concert.application.user.GetUserPointUseCase
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.griotold.concert.interfaces.controller.WebTestSupport
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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

        // when, then
        mockMvc.perform(
            get("/api/users/%d/point".format(userId))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }
}
