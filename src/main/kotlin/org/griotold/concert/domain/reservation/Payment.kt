package org.griotold.concert.domain.reservation

import org.griotold.concert.domain.common.type.PaymentStatus

data class Payment(
    val paymentId: Long = 0,
    val reservationId: Long,
    val price: Int,
    val status: PaymentStatus,
) {

    companion object {

        fun pay(reservationId: Long, price: Int): Payment {
            return Payment(
                reservationId = reservationId,
                price = price,
                status = PaymentStatus.COMPLETE,
            )
        }
    }
}
