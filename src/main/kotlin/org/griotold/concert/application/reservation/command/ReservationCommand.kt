package org.griotold.concert.application.reservation.command

import org.griotold.concert.domain.common.BadRequestException
import org.griotold.concert.domain.common.CommonResponseCode.BAD_REQUEST

class ReservationCommand {

    data class Reserve(
        val userId: Long,
        val seatId: Long,
    ) {

        init {
            if (userId <= 0) throw BadRequestException(BAD_REQUEST, "userId는 양수 값 이어야 합니다.")
            if (seatId <= 0) throw BadRequestException(BAD_REQUEST, "seatId는 양수 값 이어야 합니다.")
        }
    }

    data class Pay(
        val userId: Long,
        val reservationId: Long,
    ) {

        init {
            if (userId <= 0) throw BadRequestException(BAD_REQUEST, "userId는 양수 값 이어야 합니다.")
            if (reservationId <= 0) throw BadRequestException(BAD_REQUEST, "reservationId는 양수 값 이어야 합니다.")
        }
    }


}