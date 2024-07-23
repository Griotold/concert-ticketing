package org.griotold.concert.application.performance

import org.griotold.concert.application.UseCase
import org.griotold.concert.domain.performance.Performance
import org.griotold.concert.domain.performance.PerformanceService

@UseCase
class GetPerformanceScheduleListUseCase(
    private val performanceService: PerformanceService
) {

    operator fun invoke(performanceId: Long): Performance {
        return performanceService.getPerformance(performanceId)
    }
}
