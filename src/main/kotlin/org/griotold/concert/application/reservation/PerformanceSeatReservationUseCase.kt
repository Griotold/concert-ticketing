package org.griotold.concert.application.reservation

import org.griotold.concert.application.UseCase
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.reservation.ReservationService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService
) {

    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = performanceService.getSeatWithLock(command.seatId)
        performanceService.reserve(seat)
        reservationService.reserve(command.userId, command.seatId, seat.price)
    }
}