package org.griotold.concert.application.reservation

import org.griotold.concert.common.spring.aop.DistributedLock
import org.griotold.concert.application.common.UseCase
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.performance.Seat
import org.griotold.concert.domain.reservation.ReservationService
import org.springframework.transaction.annotation.Transactional

//@Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
) {


    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = getSeat(command.seatId)

        // 좌석 상태 변경하고, 예약 생성
        performanceService.reserve(seat, command.seatId, command.seatId, seat.price)
    }

    // 분산 락 여기에만 적용!
    @DistributedLock(key = "#lockName")
    private fun getSeat(seatId: Long) : Seat {
        return performanceService.getSeat(seatId)
    }
}