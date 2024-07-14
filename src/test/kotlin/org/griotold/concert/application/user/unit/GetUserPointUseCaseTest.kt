package org.griotold.concert.application.user.unit

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.user.GetUserPointUseCase
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
class GetUserPointUseCaseTest {

    @InjectMocks
    private lateinit var sut: GetUserPointUseCase

    @Mock
    private lateinit var userService: UserService

    @Test
    fun `유저 포인트를 조회한다`() {
        // given
        val findUser = User(1L, "유저", UserPoint(10000))
        val command = UserCommand.GetPoint(1L)

        given(userService.getUser(1L)).willReturn(findUser)

        // when
        val result = sut(command)

        // then
        verify(userService, times(1)).getUser(1L)

        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}