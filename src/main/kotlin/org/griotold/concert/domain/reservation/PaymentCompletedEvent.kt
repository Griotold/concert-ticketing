package org.griotold.concert.domain.reservation

data class PaymentCompletedEvent(
    val reservationId: Long,
    val paymentId: Long,
)
