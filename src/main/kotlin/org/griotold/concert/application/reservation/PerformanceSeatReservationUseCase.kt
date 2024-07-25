package org.griotold.concert.application.reservation

import org.griotold.concert.aop.DistributedLock
import org.griotold.concert.application.common.UseCase
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.performance.Seat
import org.griotold.concert.domain.reservation.ReservationService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService
) {


    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = getSeat(command.seatId)
        performanceService.reserve(seat)
        reservationService.reserve(command.userId, command.seatId, seat.price)
    }

    // 분산 락 여기에만 적용!
    @DistributedLock(key = "#lockName")
    private fun getSeat(seatId: Long) : Seat {
        return performanceService.getSeat(seatId)
    }
}