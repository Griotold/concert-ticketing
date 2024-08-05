package org.griotold.concert.application.performance

import org.griotold.concert.application.common.UseCase
import org.griotold.concert.domain.performance.PerformanceService
import org.griotold.concert.domain.performance.Seat

@UseCase
class GetAvailableSeatListUseCase(
    private val performanceService: PerformanceService
) {
    operator fun invoke(performanceScheduleId: Long): List<Seat> {
        return performanceService.getAvailableSeatList(performanceScheduleId)
    }
}
