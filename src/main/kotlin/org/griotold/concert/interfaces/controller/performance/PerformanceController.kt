package org.griotold.concert.interfaces.controller.performance

import org.griotold.concert.application.performance.GetAvailableSeatListUseCase
import org.griotold.concert.application.performance.GetPerformanceListUseCase
import org.griotold.concert.application.performance.GetPerformanceScheduleListUseCase
import org.griotold.concert.domain.common.Pageable
import org.griotold.concert.interfaces.controller.CommonResponse
import org.griotold.concert.interfaces.controller.common.QueueToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/performances")
class PerformanceController(
    private val getPerformanceListUseCase: GetPerformanceListUseCase,
    private val getPerformanceScheduleListUseCase: GetPerformanceScheduleListUseCase,
    private val getAvailableSeatListUseCase: GetAvailableSeatListUseCase,
) {

    @GetMapping
    fun getPerformanceList(
        @QueueToken token: String,
        @RequestParam("pageNo") pageNo: Int,
        @RequestParam("pageSize") pageSize: Int,
    ): CommonResponse<PerformanceResponse.PerformanceList> {
        val result = getPerformanceListUseCase(Pageable(pageNo, pageSize))
        return CommonResponse.ok(
            PerformanceResponse.PerformanceList.toResponse(result)
        )
    }

    @GetMapping("/{performance-id}")
    fun getPerformanceScheduleList(
        @QueueToken token: String,
        @PathVariable("performance-id") performanceId: Long,
    ): CommonResponse<PerformanceResponse.PerformanceScheduleList> {
        val result = getPerformanceScheduleListUseCase(performanceId)
        return CommonResponse.ok(
            PerformanceResponse.PerformanceScheduleList.toResponse(result)
        )
    }

    @GetMapping("/{performance-schedule-id}/seats")
    fun getAvailableSeatList(
        @QueueToken token: String,
        @PathVariable("performance-schedule-id") performanceScheduleId: Long,
    ): CommonResponse<PerformanceResponse.AvailableSeatList> {
        val result = getAvailableSeatListUseCase(performanceScheduleId)
        return CommonResponse.ok(
            PerformanceResponse.AvailableSeatList.toResponse(result)
        )
    }
}