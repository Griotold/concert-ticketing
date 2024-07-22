package org.griotold.concert.infra.db.reservation

import org.griotold.concert.domain.common.type.ReservationStatus
import org.griotold.concert.domain.reservation.Payment
import org.griotold.concert.domain.reservation.Reservation
import org.griotold.concert.domain.reservation.ReservationStore
import org.springframework.stereotype.Repository

@Repository
class ReservationStoreImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
    private val paymentJpaRepository: PaymentJpaRepository,
) : ReservationStore {

    override fun save(reservation: Reservation): Reservation {
        val reservationEntity = ReservationEntity.of(reservation)
        return reservationJpaRepository.save(reservationEntity).toDomain()
    }

    override fun save(payment: Payment): Payment {
        val paymentEntity = PaymentEntity.of(payment)
        return paymentJpaRepository.save(paymentEntity).toDomain()
    }

    override fun cancelExpiredReservation(ids: List<Long>) {
        reservationJpaRepository.updateStatus(ids, ReservationStatus.EXPIRED)
    }
}
