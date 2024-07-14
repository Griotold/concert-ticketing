package org.griotold.concert.application.user.command

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.domain.common.BadRequestException
import org.junit.jupiter.api.Test

class UserCommandTest {

    @Test
    fun `포인트 조회 시 userId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = -1L

        // when, then
        assertThatThrownBy { UserCommand.GetPoint(userId) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("userId는 양수 값 이어야 합니다.")
    }

    @Test
    fun `포인트 충전 시 userId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = -1L
        val amount = 0

        // when, then
        assertThatThrownBy { UserCommand.ChargePoint(userId, amount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("userId는 양수 값 이어야 합니다.")
    }

    @Test
    fun `포인트 충전 시 amount는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = 1L
        val amount = -1

        // when, then
        assertThatThrownBy { UserCommand.ChargePoint(userId, amount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("amount는 양수 값 이어야 합니다.")
    }
}

