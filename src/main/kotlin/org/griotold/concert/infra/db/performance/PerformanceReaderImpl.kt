package org.griotold.concert.infra.db.performance

import org.griotold.concert.domain.common.PageInfo
import org.griotold.concert.domain.common.Pageable
import org.griotold.concert.domain.common.WithPage
import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.domain.performance.Performance
import org.griotold.concert.domain.performance.PerformanceReader
import org.griotold.concert.domain.performance.Seat
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PerformanceReaderImpl(
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,

    ) : PerformanceReader {
    override fun getPerformanceList(pageable: Pageable): WithPage<Performance> {
        val pageRequest = PageRequest.of(pageable.pageNo - 1, pageable.pageSize)
        val result = performanceJpaRepository.findAll(pageRequest)

        return WithPage(
            result.content.map { it.toDomain() },
            PageInfo(pageable.pageNo, pageable.pageSize, result.totalElements)
        )
    }

    /**
     * 여기서 let 함수는 널 검사와 작업 수행을 위해 사용
     * let 블록 내에서 널이 아닌 객체에 대해 필요한 작업을 수행하고, 널인 경우에는 블록을 건너뛰도록 합니다.
     * */
    override fun getPerformance(performanceId: Long): Performance? {
        return performanceJpaRepository.findById(performanceId).orElse(null)
            ?.let {
                val performanceScheduleEntities = performanceScheduleJpaRepository.findByPerformanceId(performanceId)
                return Performance(
                    performanceId = performanceId,
                    title = it.title,
                    content = it.content,
                    schedules = performanceScheduleEntities.map { schedule -> schedule.toDomain() }
                )
            }
    }

    override fun getAvailableSeatList(performanceScheduleId: Long): List<Seat> {
        return seatJpaRepository.findByPerformanceScheduleIdAndStatus(performanceScheduleId, SeatStatus.OPEN)
            .map { it.toDomain() }
    }

    override fun getSeatWithLock(seatId: Long): Seat? {
        return seatJpaRepository.findByIdForUpdate(seatId)
            ?.toDomain()
    }

    // 낙관적 락
    override fun getSeat(seatId: Long): Seat? {
        return seatJpaRepository.findByIdOrNull(seatId)
            ?.toDomain()

    }
}
