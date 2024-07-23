package org.griotold.concert.application.reservation

import org.griotold.concert.application.UseCase
import org.griotold.concert.application.reservation.command.ReservationCommand
import org.griotold.concert.domain.reservation.ReservationService
import org.griotold.concert.domain.user.UserService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class ReservationPaymentUseCase(
    private val userService: UserService,
    private val reservationService: ReservationService
) {

    operator fun invoke(command: ReservationCommand.Pay) {
        val reservation = reservationService.getReservationWithLock(command.reservationId)
        val user = userService.getUser(command.userId)

        reservationService.pay(user, reservation)
        userService.usePoint(user, reservation.price)
    }
}