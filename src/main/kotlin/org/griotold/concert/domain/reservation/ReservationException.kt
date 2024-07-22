package org.griotold.concert.domain.reservation

import org.griotold.concert.domain.common.CustomException
import org.griotold.concert.domain.common.ResponseCode

class ReservationException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(reservationResponseCode: ReservationResponseCode) : super(reservationResponseCode.msg) {
        this.responseCode = reservationResponseCode
        this.msg = reservationResponseCode.msg
    }

    constructor(reservationResponseCode: ReservationResponseCode, message: String) : super(message) {
        this.responseCode = reservationResponseCode
        this.msg = message
    }
}
