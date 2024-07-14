package org.griotold.concert.domain.user

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun `유저 포인트 충전 시 기존 수량에 요청 수량이 더한 값을 응답한다`() {
        // given
        val user = User(1L, "유저", UserPoint(0))
        val chargeAmount = 10000

        // when
        val result = user.chargePoint(chargeAmount)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(10000)
    }

    @Test
    fun `유저 포인트 사용 시 기존 수량에 요청 수량을 뺀 값을 응답한다`() {
        // given
        val user = User(1L, "유저", UserPoint(10000))
        val useAmount = 5000

        // when
        val result = user.usePoint(useAmount)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(5000)
    }

    @Test
    fun `유저 포인트 사용 시 잔액 보다 요청 수량이 많으면 UserException이 발생한다`() {
        // given
        val user = User(1L, "유저", UserPoint(3000))
        val useAmount = 5000

        // when, then
        assertThatThrownBy { user.usePoint(useAmount) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("잔액이 부족합니다.")
    }

}