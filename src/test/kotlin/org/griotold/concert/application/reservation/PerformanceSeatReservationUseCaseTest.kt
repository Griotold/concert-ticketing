package org.griotold.concert.application.reservation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.domain.common.type.ReservationStatus
import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.domain.performance.PerformanceException
import org.griotold.concert.infra.db.performance.SeatEntity
import org.griotold.concert.infra.db.performance.SeatJpaRepository
import org.griotold.concert.infra.db.reservation.ReservationJpaRepository
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync

class PerformanceSeatReservationUseCaseTest(
    private val sut: PerformanceSeatReservationUseCase,
    private val seatJpaRepository: SeatJpaRepository,
    private val reservationJpaRepository: ReservationJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `좌석 예약 동시성 테스트 3,000명 비관적락`() {
        // given
        val seatEntity = SeatEntity(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.OPEN
        )
        seatJpaRepository.save(seatEntity)

        // when
        val userCount = 3_000
        val futures = Array(userCount) { i ->
            runAsync { sut(ReservationCommand.Reserve(userId = i.toLong() + 1, seatId = 1L)) }
        }

        allOf(*futures)
            .exceptionally { null }
            .join()

        // then
        val errorCount = futures.count { it.isCompletedExceptionally }
        assertThat(errorCount).isEqualTo(userCount - 1)

        val reservationCount = reservationJpaRepository.count()
        assertThat(reservationCount).isEqualTo(1)
    }


    @Test
    fun `좌석을 예약할 수 있다`() {
        // given
        val seatEntity = SeatEntity(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.OPEN
        )
        seatJpaRepository.save(seatEntity)

        val command = ReservationCommand.Reserve(userId = 1L, seatId = 1L)

        // when
        sut(command)

        // then
        val seat = seatJpaRepository.findById(1L).get()
        assertThat(seat.id).isEqualTo(1L)
        assertThat(seat.performanceScheduleId).isEqualTo(1L)
        assertThat(seat.seatNo).isEqualTo(1)
        assertThat(seat.price).isEqualTo(10000)
        assertThat(seat.status).isEqualTo(SeatStatus.RESERVED)

        val reservation = reservationJpaRepository.findById(1L).get()
        assertThat(reservation.id).isEqualTo(1L)
        assertThat(reservation.seatId).isEqualTo(1L)
        assertThat(reservation.userId).isEqualTo(1L)
        assertThat(reservation.status).isEqualTo(ReservationStatus.RESERVED)
        assertThat(reservation.expiredAt).isNotNull()
    }

    @Test
    fun `좌석 상태가 RESERVED 이면 PerformanceException 이 발생한다`() {
        // given
        val seatEntity = SeatEntity(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.RESERVED
        )
        seatJpaRepository.save(seatEntity)

        val command = ReservationCommand.Reserve(userId = 1L, seatId = 1L)

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(PerformanceException::class.java)
            .hasMessage("이미 예약된 좌석입니다.")
    }
}