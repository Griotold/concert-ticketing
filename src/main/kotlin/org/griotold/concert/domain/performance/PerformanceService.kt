package org.griotold.concert.domain.performance

import org.griotold.concert.domain.common.Pageable
import org.griotold.concert.domain.common.WithPage
import org.griotold.concert.domain.performance.PerformanceResponseCode.PERFORMANCE_NOT_FOUND
import org.griotold.concert.domain.performance.PerformanceResponseCode.PERFORMANCE_SEAT_NOT_FOUND
import org.griotold.concert.domain.reservation.Reservation
import org.griotold.concert.domain.reservation.ReservationStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PerformanceService(
    private val performanceReader: PerformanceReader,
    private val performanceStore: PerformanceStore,
    private val reservationStore: ReservationStore,
) {
    fun getPerformance(performanceId: Long): Performance {
        return performanceReader.getPerformance(performanceId)
            ?: throw PerformanceException(PERFORMANCE_NOT_FOUND)
    }

    fun getPerformanceList(pageable: Pageable): WithPage<Performance> {
        return performanceReader.getPerformanceList(pageable)
    }

    fun getAvailableSeatList(performanceScheduleId: Long): List<Seat> {
        return performanceReader.getAvailableSeatList(performanceScheduleId)
    }

    fun getSeatWithLock(seatId: Long): Seat {
        return performanceReader.getSeatWithLock(seatId)
            ?: throw PerformanceException(PERFORMANCE_SEAT_NOT_FOUND)
    }

    @Transactional
    fun reserve(seat: Seat, userId: Long, seatId: Long, price: Int) {
        val newSeat = seat.reserve()
        performanceStore.save(newSeat)

        val reservation = Reservation.reserve(userId, seatId, price)
        reservationStore.save(reservation)
    }

    fun openSeat(seatIds: List<Long>) {
        performanceStore.openSeat(seatIds)
    }

    // 낙관적락
    fun getSeat(seatId: Long): Seat {
        return performanceReader.getSeat(seatId)
            ?: throw PerformanceException(PERFORMANCE_SEAT_NOT_FOUND)
    }
}
