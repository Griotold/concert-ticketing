package org.griotold.concert.infra.db.reservation

import org.griotold.concert.domain.common.type.ReservationStatus
import org.griotold.concert.domain.reservation.Reservation
import org.griotold.concert.domain.reservation.ReservationReader
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReservationReaderImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationReader {

    // 낙관적 락을 사용할거라 그냥 findById() 호출
    override fun getReservationWithLock(reservationId: Long): Reservation? {
        return reservationJpaRepository.findById(reservationId).get()
            ?.toDomain()
    }

    override fun getExpiredReservations(): List<Reservation> {
        val status = ReservationStatus.RESERVED
        val date = LocalDateTime.now()
        return reservationJpaRepository
            .findAllByStatusAndExpiredAtBefore(status, date)
            .map { it.toDomain() }
    }
}
