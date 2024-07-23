package org.griotold.concert.interfaces.controller.reservation

class ReservationRequest {

    data class ReserveSeat(
        val userId: Long,
        val seatId: Long,
    )

    data class Payment(
        val userId: Long,
    )
}