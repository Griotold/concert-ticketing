package org.griotold.concert.interfaces.eventlstener.reservation

import org.griotold.concert.domain.reservation.PaymentCompletedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationEventListener {
    private val log: Logger get() = LoggerFactory.getLogger(this::class.java)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: PaymentCompletedEvent) {
        log.info("event: {}", event.toString())
    }
}