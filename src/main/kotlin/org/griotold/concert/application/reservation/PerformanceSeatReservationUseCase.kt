package org.griotold.concert.application.reservation

import org.griotold.concert.aop.DistributedLock
import org.griotold.concert.application.common.UseCase
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.reservation.ReservationService

//@Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService
) {
    // 분산락 사용 - Redisson
    @DistributedLock(key = "#lockName")
    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = performanceService.getSeat(command.seatId)
        performanceService.reserve(seat)
        reservationService.reserve(command.userId, command.seatId, seat.price)
    }
}