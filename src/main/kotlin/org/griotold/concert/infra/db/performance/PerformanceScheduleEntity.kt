package org.griotold.concert.infra.db.performance

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.griotold.concert.domain.performance.PerformanceSchedule
import org.griotold.concert.infra.db.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "performance_schedule")
class PerformanceScheduleEntity(
    val performanceId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val reservationAt: LocalDateTime,
) : BaseEntity() {

    fun toDomain(): PerformanceSchedule {
        return PerformanceSchedule(
            performanceScheduleId = id!!,
            startAt = startAt,
            endAt = endAt,
            reservationAt = reservationAt,
        )
    }
}
