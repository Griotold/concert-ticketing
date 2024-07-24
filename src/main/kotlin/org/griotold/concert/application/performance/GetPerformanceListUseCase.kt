package org.griotold.concert.application.performance

import org.griotold.concert.application.UseCase
import org.griotold.concert.domain.common.Pageable
import org.griotold.concert.domain.common.WithPage
import org.griotold.concert.domain.performance.Performance
import org.griotold.concert.domain.performance.PerformanceService

@UseCase
class GetPerformanceListUseCase(
    private val performanceService: PerformanceService
) {

    operator fun invoke(pageable: Pageable): WithPage<Performance> {
        return performanceService.getPerformanceList(pageable)
    }
}
