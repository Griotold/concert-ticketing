package org.griotold.concert.infra.db.performance

import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.domain.performance.PerformanceStore
import org.griotold.concert.domain.performance.Seat
import org.springframework.stereotype.Repository

@Repository
class PerformanceStoreImpl(
    private val seatJpaRepository: SeatJpaRepository,
) : PerformanceStore {

    override fun save(seat: Seat): Seat {
        val seatEntity = SeatEntity.of(seat)
        return seatJpaRepository.save(seatEntity).toDomain()
    }

    override fun openSeat(seatIds: List<Long>) {
        seatJpaRepository.updateStatus(seatIds, SeatStatus.OPEN)
    }
}
