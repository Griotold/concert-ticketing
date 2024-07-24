package org.griotold.concert.domain.performance

interface PerformanceStore {

    fun save(seat: Seat): Seat

    fun openSeat(seatIds: List<Long>)
}
