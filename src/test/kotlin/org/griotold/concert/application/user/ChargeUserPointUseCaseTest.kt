package org.griotold.concert.application.user

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.UserException
import org.griotold.concert.infra.db.user.PointHistoryJpaRepository
import org.griotold.concert.infra.db.user.UserEntity
import org.griotold.concert.infra.db.user.UserJpaRepository
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
@Disabled
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class ChargeUserPointUseCaseTest(
    // sut : System Under Test
    private val sut: ChargeUserPointUseCase,
    private val userJpaRepository: UserJpaRepository,
    private val pointHistoryJpaRepository: PointHistoryJpaRepository,
) {

    @Test
    fun `유저 포인트를 충전한다`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))

        val command = UserCommand.ChargePoint(1L, 1000)

        // when
        val result = sut(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(1000)
    }

    @Test
    fun `유저 포인트 충전 시 유저가 없으면 UserException이 발생한다`() {
        // given
        val command = UserCommand.ChargePoint(1L, 1000)

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("유저를 찾을 수 없습니다.")
    }
}
