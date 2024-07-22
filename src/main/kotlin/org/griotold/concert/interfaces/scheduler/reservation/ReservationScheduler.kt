package org.griotold.concert.interfaces.scheduler.reservation

import org.griotold.concert.application.reservation.CancelExpiredReservationUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ReservationScheduler(
    private val cancelExpiredReservationUseCase: CancelExpiredReservationUseCase,
) {

    @Scheduled(fixedDelay = 5000)
    fun cancelExpiredReservation() {
        cancelExpiredReservationUseCase()
    }
}