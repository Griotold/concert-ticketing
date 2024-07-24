package org.griotold.concert.application.reservation

import org.griotold.concert.application.UseCase
import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.reservation.ReservationService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class CancelExpiredReservationUseCase(
    private val reservationService: ReservationService,
    private val performanceService: PerformanceService,
) {

    operator fun invoke() {
        val seatIds = reservationService.cancelExpiredReservation()
        performanceService.openSeat(seatIds)
    }
}