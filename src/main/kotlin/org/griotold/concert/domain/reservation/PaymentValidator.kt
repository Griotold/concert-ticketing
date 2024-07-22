package org.griotold.concert.domain.reservation

import org.griotold.concert.domain.common.type.ReservationStatus
import org.griotold.concert.domain.reservation.ReservationResponseCode.*
import org.griotold.concert.domain.user.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PaymentValidator {

    fun validate(user: User, reservation: Reservation) {
        if (user.userId != reservation.userId) {
            throw ReservationException(NOT_RESERVATION_USER)
        }

        if (reservation.status != ReservationStatus.RESERVED) {
            throw ReservationException(NOT_RESERVED)
        }

        if (reservation.expiredAt.isBefore(LocalDateTime.now())) {
            throw ReservationException(RESERVATION_EXPIRED)
        }
    }
}
