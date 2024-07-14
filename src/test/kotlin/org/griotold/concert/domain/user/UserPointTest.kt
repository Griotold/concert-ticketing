package org.griotold.concert.domain.user

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.griotold.concert.domain.common.BadRequestException
import org.junit.jupiter.api.Test

class UserPointTest {

    @Test
    fun `포인트를 음수로 초기화 하면 BadRequestException 이 발생한다`(){
        // given

        // when, then
        assertThatThrownBy { UserPoint(-1) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("포인트는 음수로 초기화 할 수 없습니다.")
    }

    @Test
    fun `포인트를 충전한다`() {
        // given
        val userPoint = UserPoint(0)
        val chargeAmount = 10000

        // when
        val result = userPoint.charge(chargeAmount)

        // then
        assertThat(result.amount).isEqualTo(chargeAmount)
    }

    @Test
    fun `포인트 충전 시 음수값이면 BadRequestException 이 발생한다`() {
        // given
        val userPoint = UserPoint(0)
        val chargeAmount = -1

        // when, then
        assertThatThrownBy { userPoint.charge(chargeAmount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("충전 포인트는 0 이상 이어야 합니다.")
    }

    @Test
    fun `포인트를 사용한다`() {
        // given
        val userPoint = UserPoint(10000)
        val useAmount = 5000

        // when
        val result = userPoint.use(useAmount)

        // then
        assertThat(result.amount).isEqualTo(5000)
    }

    @Test
    fun `포인트를 사용 시 음수 값이면 BadRequestException 이 발생한다`() {
        // given
        val userPoint = UserPoint(10000)
        val useAmount = -1

        // when, then
        assertThatThrownBy { userPoint.use(useAmount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("사용 포인트는 0 이상 이어야 합니다.")
    }

    @Test
    fun `포인트 사용 시 잔액이 부족하면 UserException 이 발생한다`() {
        // given
        val userPoint = UserPoint(0)
        val useAmount = 10000

        // when, then
        assertThatThrownBy { userPoint.use(useAmount) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("잔액이 부족합니다.")
    }
}