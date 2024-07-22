package org.griotold.concert.domain.reservation

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.griotold.concert.domain.common.type.ReservationStatus
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

class PaymentValidatorTest {
    private val paymentValidator = PaymentValidator()

    @Test
    fun `결제 요청 검증 성공`() {
        // given
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = LocalDateTime.now().plusMinutes(5),
        )
        val user = User(1L, "유저", UserPoint(0))

        // when
        paymentValidator.validate(user, reservation)

        // then
        assertThat(true)
    }

    @Test
    fun `결제 요청 시 예약한 유저가 아니면 ReservationException 이 발생한다`() {
        // given
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = LocalDateTime.now().plusMinutes(5),
        )
        val user = User(2L, "유저", UserPoint(0))

        // when, then
        assertThatThrownBy { paymentValidator.validate(user, reservation) }
            .isInstanceOf(ReservationException::class.java)
            .hasMessage("예약한 유저가 아닙니다.")
    }

    @ParameterizedTest
    @ValueSource(strings = ["COMPLETE", "CANCEL", "EXPIRED"])
    fun `결제 요청 시 예약 상태가 아니면 ReservationException 이 발생한다`(status: ReservationStatus) {
        // given
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = status,
            expiredAt = LocalDateTime.now().plusMinutes(5),
        )
        val user = User(1L, "유저", UserPoint(0))

        // when, then
        assertThatThrownBy { paymentValidator.validate(user, reservation) }
            .isInstanceOf(ReservationException::class.java)
            .hasMessage("예약상태가 아닙니다.")
    }

    @Test
    fun `결제 요청 시 예약 가능시간이 만료되었으면 ReservationException 이 발생한다`() {
        // given
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = LocalDateTime.now().minusMinutes(5),
        )
        val user = User(1L, "유저", UserPoint(0))

        // when, then
        assertThatThrownBy { paymentValidator.validate(user, reservation) }
            .isInstanceOf(ReservationException::class.java)
            .hasMessage("예약이 만료되었습니다.")
    }
}