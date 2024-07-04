package org.griotold.concert.controller

import org.griotold.concert.controller.dto.ReservationRequest
import org.griotold.concert.controller.dto.ReservationResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservations")
class ReservationController {

    @PostMapping
    fun reserve(
        @RequestBody req: ReservationRequest,
        @RequestHeader("token") token: String
    ): ResponseEntity<ReservationResponse> {
        // 하드코딩된 값으로 ReservationResponse 객체 생성
        val response = ReservationResponse(
            reservationId = 100L,
            concertInfo = ReservationResponse.ConcertInfo(
                concertId = req.concertId,
                concertScheduleId = req.concertScheduleId,
                name = "Sample Concert",
                concertStartAt = "2024-07-01T20:00:00",
                seatId = req.seatId,
                seatNum = 10
            )
        )

        // ResponseEntity.ok() 메서드를 사용하여 응답 생성 및 반환
        return ResponseEntity.ok(response)
    }
}