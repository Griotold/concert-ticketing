package org.griotold.concert.application.user

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.UserException
import org.griotold.concert.infra.db.user.PointHistoryJpaRepository
import org.griotold.concert.infra.db.user.UserEntity
import org.griotold.concert.infra.db.user.UserJpaRepository
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync

class ChargeUserPointUseCaseTest(
    private val sut: ChargeUserPointUseCase,
    private val userJpaRepository: UserJpaRepository,
    private val pointHistoryJpaRepository: PointHistoryJpaRepository,
) : IntegrationTestSupport() {

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

    @Test
    fun `포인트 충전 동시성 테스트 - 10번 - 비관적 락 사용하여 모두 충전`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))

        // when
        val trialCount = 10
        val amount = 10000

        val futures = Array(trialCount) { i ->
            runAsync { sut(UserCommand.ChargePoint(userId = 1L, amount = amount))}
        }

        allOf(*futures)
            .exceptionally {
                println(it.localizedMessage)
                null
            }
            .join()

        // then
        val result = userJpaRepository.findById(1L).get()
        val count = pointHistoryJpaRepository.count()

        assertThat(result.point).isEqualTo(trialCount * amount)
        assertThat(count.toInt()).isEqualTo(trialCount)
    }
}
