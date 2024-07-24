package org.griotold.concert.domain.reservation

interface ReservationStore {

    fun save(reservation: Reservation): Reservation

    fun save(payment: Payment): Payment

    fun cancelExpiredReservation(ids: List<Long>)
}
