package org.griotold.concert.domain.reservation

import org.griotold.concert.domain.common.ResponseCode

enum class ReservationResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    RESERVATION_NOT_FOUND(3404, "예약이 없습니다."),
    NOT_RESERVATION_USER(3403, "예약한 유저가 아닙니다."),
    RESERVATION_EXPIRED(3000, "예약이 만료되었습니다."),
    NOT_RESERVED(3001, "예약상태가 아닙니다."),
}
