package org.griotold.concert.infra.event.reservation

import org.griotold.concert.domain.reservation.PaymentCompletedEvent
import org.griotold.concert.domain.reservation.ReservationEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ReservationEventPublisherImpl(
    private val publisher: ApplicationEventPublisher,
) : ReservationEventPublisher{

    override fun publish(event: PaymentCompletedEvent) {
        publisher.publishEvent(event)
    }
}