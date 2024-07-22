package org.griotold.concert.domain.reservation

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.griotold.concert.domain.common.type.ReservationStatus
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationTest {

    @Test
    fun `userId, seatId로 예약시 상태는 RESERVED, 만료시간은 5분 이다`() {
        // given
        val userId = 1L
        val seatId = 1L
        val price = 10000

        // when
        val result = Reservation.reserve(userId, seatId, price)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.seatId).isEqualTo(1L)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(ReservationStatus.RESERVED)
    }

    @Test
    fun `결제 완료 시 예약 상태가 COMPLETE 로 변경된다`() {
        // given
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = LocalDateTime.now(),
        )

        // when
        val result = reservation.paymentComplete()

        // then
        assertThat(result.reservationId).isEqualTo(1L)
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.seatId).isEqualTo(1L)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(ReservationStatus.COMPLETE)
    }
}