package org.griotold.concert.application.user

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.UserException
import org.griotold.concert.infra.db.user.UserEntity
import org.griotold.concert.infra.db.user.UserJpaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class GetUserPointUseCaseTest(
    private val sut: GetUserPointUseCase,
    private val userJpaRepository: UserJpaRepository,
) {
    @Test
    fun `유저 포인트를 조회한다`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))

        val command = UserCommand.GetPoint(1L)

        // when
        val result = sut(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(0)
    }

    @Test
    fun `유저 포인트 조회 시 유저가 없으면 UserException 이 발생한다`() {
        // given
        val command = UserCommand.GetPoint(1L)

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("유저를 찾을 수 없습니다.")
    }
}