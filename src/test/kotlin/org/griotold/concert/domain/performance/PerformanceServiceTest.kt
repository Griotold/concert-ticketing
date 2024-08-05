package org.griotold.concert.domain.performance

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.domain.common.type.SeatStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class PerformanceServiceTest {

    // sut : System Under Test
    @InjectMocks
    private lateinit var sut: PerformanceService

    @Mock
    private lateinit var performanceReader: PerformanceReader

    @Mock
    private lateinit var performanceStore: PerformanceStore

    @Test
    fun `공연 정보가 없으면 PerformanceException 이 발생한다`() {
        // given

        // when, then
        assertThatThrownBy { sut.getPerformance(1L) }
            .isInstanceOf(PerformanceException::class.java)
            .hasMessage("공연을 찾을 수 없습니다.")
    }

    @Test
    fun `좌석정보가 없으면 PerformanceException 이 발생한다`() {
        // given

        // when, then
        assertThatThrownBy { sut.getSeatWithLock(1L) }
            .isInstanceOf(PerformanceException::class.java)
            .hasMessage("좌석을 찾을 수 없습니다.")
    }

    @Test
    fun `좌석을 예약상태에서 예약가능상태로 변경한다`() {
        // given
        val seatIds = listOf(1L, 2L)

        // when
        sut.openSeat(seatIds)

        // then
        verify(performanceStore, times(1)).openSeat(seatIds)
    }

    @Test
    fun `공연 스케줄 아이디로 가능한 좌석 리스트를 반환한다`() {
        // given
        val performanceScheduleId = 1L
        val seats = listOf<Seat>()
        whenever(performanceReader.getAvailableSeatList(performanceScheduleId)).thenReturn(seats)

        // when
        val result = sut.getAvailableSeatList(performanceScheduleId)

        // then
        assertThat(result).isEqualTo(seats)
    }

    @Test
    fun `좌석을 예약한다`() {
        // given
        val seat = Seat(
            seatId = 1L,
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            status = SeatStatus.OPEN,
        )
        val reservedSeat = seat.copy(status = SeatStatus.RESERVED)
        val userId = 1L

        // when
        sut.reserve(seat, userId, seat.seatId, seat.price)

        // then
        verify(performanceStore, times(1)).save(reservedSeat)
    }
}
