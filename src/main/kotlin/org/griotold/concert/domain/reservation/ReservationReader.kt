package org.griotold.concert.domain.reservation

interface ReservationReader {

    fun getReservationWithLock(reservationId: Long): Reservation?

    fun getExpiredReservations(): List<Reservation>
}
