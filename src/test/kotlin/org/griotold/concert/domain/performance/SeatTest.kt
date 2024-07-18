package org.griotold.concert.domain.performance

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.domain.common.type.SeatStatus
import org.junit.jupiter.api.Test

class SeatTest {

    @Test
    fun `좌석 예약 시 좌석 상태가 RESERVED 로 변경된다`() {
        // given
        val seat = Seat(
            seatId = 1L,
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            status = SeatStatus.OPEN,
        )

        // when
        val result = seat.reserve()

        // then
        assertThat(result.seatId).isEqualTo(1L)
        assertThat(result.performanceScheduleId).isEqualTo(1L)
        assertThat(result.seatNo).isEqualTo(1)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(SeatStatus.RESERVED)
    }

    @Test
    fun `좌석 예약 시 좌석 상태가 RESERVED 이면 PerformanceException 이 발생한다`() {
        // given
        val seat = Seat(
            seatId = 1L,
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            status = SeatStatus.RESERVED,
        )

        // when, then
        assertThatThrownBy { seat.reserve() }
            .isInstanceOf(PerformanceException::class.java)
            .hasMessage("이미 예약된 좌석입니다.")
    }
}