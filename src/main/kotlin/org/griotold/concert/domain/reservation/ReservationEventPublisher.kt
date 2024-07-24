package org.griotold.concert.domain.reservation

interface ReservationEventPublisher {

    fun publish(event: PaymentCompletedEvent)
}
