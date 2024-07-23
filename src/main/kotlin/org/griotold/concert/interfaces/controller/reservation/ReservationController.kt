package org.griotold.concert.interfaces.controller.reservation

import org.griotold.concert.application.reservation.PerformanceSeatReservationUseCase
import org.griotold.concert.application.reservation.ReservationPaymentUseCase
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.interfaces.controller.CommonResponse
import org.griotold.concert.interfaces.controller.common.QueueToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservations")
class ReservationController(
    private val performanceSeatReservationUseCase: PerformanceSeatReservationUseCase,
    private val reservationPaymentUseCase: ReservationPaymentUseCase,
) {

    @PostMapping
    fun reserveSeat(
        @QueueToken token: String,
        @RequestBody request: ReservationRequest.ReserveSeat,
    ): CommonResponse<Unit> {
        val command = ReservationCommand.Reserve(request.userId, request.seatId)
        performanceSeatReservationUseCase(command)
        return CommonResponse.ok()
    }

    @PostMapping("/{reservation-id}/payments")
    fun payment(
        @PathVariable(name = "reservation-id") reservationId: Long,
        @RequestBody request: ReservationRequest.Payment
    ): CommonResponse<Void> {

        val command = ReservationCommand.Pay(
            userId = request.userId,
            reservationId = reservationId,
        )

        reservationPaymentUseCase(command)
        return CommonResponse.ok()
    }
}