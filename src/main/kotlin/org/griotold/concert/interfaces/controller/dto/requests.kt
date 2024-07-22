package org.griotold.concert.interfaces.controller.dto

data class IssueTokenRequest(val memberId: Long)

data class ReservationRequest(
    val concertId: Long,
    val concertScheduleId: Long,
    val seatId: Long,
    val memberId: Long
)

data class ChargeRequest(
    val amount: Long
)

data class PayRequest(
    val memberId: Long
)
