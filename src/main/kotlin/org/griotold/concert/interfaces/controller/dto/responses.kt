package org.griotold.concert.interfaces.controller.dto

data class IssueTokenResponse(
    val token: String,
    val memberId: Long,
    val waitingNum: Int
)

data class ConcertScheduleResponse(
    val concertId: Long,
    val schedules: List<ScheduleDTO>
) {
    data class ScheduleDTO(
        val concertScheduleId: Long,
        val concertStartAt: String
    )
}

data class ConcertSeatResponse(
    val concertScheduleId: Long,
    val seats: List<SeatDTO>
) {
    data class SeatDTO(
        val seatId: Long,
        val seatNum: Int
    )
}

data class ReservationResponse(
    val reservationId: Long,
    val concertInfo: ConcertInfo
) {
    data class ConcertInfo(
        val concertId: Long,
        val concertScheduleId: Long,
        val name: String,
        val concertStartAt: String,
        val seatId: Long,
        val seatNum: Int
    )
}

data class ChargeResponse(
    val memberId: Long,
    val transactionType: String,
    val amount: Long
)

data class PointResponse(
    val memberId: Long,
    val points: Int
)

// {
//  "paymentId" : number
//  "paymentPrice" : number
//  "status" : string,
//  "paidAt" : String
//}

data class PayResponse(
    val paymentId: Long,
    val paymentPrice: Int,
    val status : String,
    val paidAt : String
)
