package org.griotold.concert.application.user.unit

import org.assertj.core.api.Assertions.*
import org.griotold.concert.application.user.ChargeUserPointUseCase
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.griotold.concert.domain.user.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class ChargeUserPointUseCaseTest {

    @InjectMocks
    private lateinit var sut: ChargeUserPointUseCase

    @Mock
    private lateinit var userService: UserService

    @Test
    fun `유저 포인트를 충전한다`() {
        // given
        val findUser = User(1L, "유저", UserPoint(0))
        val afterCharge = User(1L, "유저", UserPoint(10000))

        val command = UserCommand.ChargePoint(1L, 10000)

        given(userService.getUserWithLock(1L)).willReturn(findUser)
        given(userService.chargePoint(findUser, 10000)).willReturn(afterCharge)

        // when
        val result = sut(command)

        // then
        verify(userService, times(1)).getUserWithLock(1L)
        verify(userService, times(1)).chargePoint(findUser, 10000)

        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}
