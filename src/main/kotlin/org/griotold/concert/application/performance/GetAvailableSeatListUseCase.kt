package org.griotold.concert.application.performance

import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.performance.Seat
import org.springframework.stereotype.Service

@Service
class GetAvailableSeatListUseCase(
    private val performanceService: PerformanceService
) {
    operator fun invoke(performanceScheduleId: Long): List<Seat> {
        return performanceService.getAvailableSeatList(performanceScheduleId)
    }
}