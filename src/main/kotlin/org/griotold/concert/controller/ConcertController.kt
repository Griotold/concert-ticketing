package org.griotold.concert.controller

import org.griotold.concert.controller.dto.ConcertScheduleResponse
import org.griotold.concert.controller.dto.ConcertSeatResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/concerts")
class ConcertController {

    @GetMapping("/{concertId}")
    fun getConcertSchedules(
        @PathVariable concertId: Long,
        @RequestHeader("token") token: String
    ): ResponseEntity<ConcertScheduleResponse> {
        // 하드코딩된 값으로 ConcertScheduleResponse 객체 생성
        val response = ConcertScheduleResponse(
            concertId = concertId,
            schedules = listOf(
                ConcertScheduleResponse.ScheduleDTO(
                    concertScheduleId = 1L,
                    concertStartAt = "2024-07-01T20:00:00"
                ),
                ConcertScheduleResponse.ScheduleDTO(
                    concertScheduleId = 2L,
                    concertStartAt = "2024-07-02T20:00:00"
                )
            )
        )

        // ResponseEntity.ok() 메서드를 사용하여 응답 생성 및 반환
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{concertId}/{concertScheduleId}")
    fun getSeats(
        @PathVariable("concertId") concertId: Long,
        @PathVariable("concertScheduleId") concertScheduleId: Long,
        @RequestHeader("token") token: String
    ) : ResponseEntity<ConcertSeatResponse> {
        // 하드코딩된 값으로 ConcertScheduleResponse 객체 생성
        val response = ConcertSeatResponse(
            concertScheduleId = concertScheduleId,
            seats = listOf(
                ConcertSeatResponse.SeatDTO(
                    seatId = 1L,
                    seatNum = 1
                ),
                ConcertSeatResponse.SeatDTO(
                    seatId = 2L,
                    seatNum = 2
                )
            )
        )

            // ResponseEntity.ok() 메서드를 사용하여 응답 생성 및 반환
        return ResponseEntity.ok(response)
    }
}