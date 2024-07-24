package org.griotold.concert.domain.reservation

import org.griotold.concert.domain.user.User
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationReader: ReservationReader,
    private val reservationStore: ReservationStore,
    private val paymentValidator: PaymentValidator,
    private val reservationEventPublisher: ReservationEventPublisher,
) {

    fun getReservationWithLock(reservationId: Long): Reservation {
        return reservationReader.getReservationWithLock(reservationId)
            ?: throw ReservationException(ReservationResponseCode.RESERVATION_NOT_FOUND)
    }

    fun reserve(userId: Long, seatId: Long, price: Int) {
        val reservation = Reservation.reserve(userId, seatId, price)
        reservationStore.save(reservation)
    }

    fun pay(user: User, reservation: Reservation) {
        paymentValidator.validate(user, reservation)

        val paymentCompleted = reservation.paymentComplete()
        reservationStore.save(paymentCompleted)

        val payment = Payment.pay(reservation.reservationId, reservation.price)
        val saved = reservationStore.save(payment)

        reservationEventPublisher
            .publish(
                PaymentCompletedEvent(
                    reservationId = reservation.reservationId,
                    paymentId = saved.paymentId
                )
            )
    }

    fun cancelExpiredReservation(): List<Long> {
        val expiredReservations = reservationReader.getExpiredReservations()
        val expiredReservationIds = expiredReservations.map { it.reservationId }
        reservationStore.cancelExpiredReservation(expiredReservationIds)
        return expiredReservations.map { it.seatId }
    }
}
