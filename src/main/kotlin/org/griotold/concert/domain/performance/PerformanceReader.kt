package org.griotold.concert.domain.performance

import org.griotold.concert.domain.common.Pageable
import org.griotold.concert.domain.common.WithPage

interface PerformanceReader {

    fun getPerformanceList(pageable: Pageable): WithPage<Performance>

    fun getPerformance(performanceId: Long): Performance?

    fun getAvailableSeatList(performanceScheduleId: Long): List<Seat>

    fun getSeatWithLock(seatId: Long): Seat?

    // 낙관적 락
    fun getSeat(seatId: Long): Seat?
}
