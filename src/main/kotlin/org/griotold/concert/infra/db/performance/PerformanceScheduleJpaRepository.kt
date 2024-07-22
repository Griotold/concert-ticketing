package org.griotold.concert.infra.db.performance

import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceScheduleJpaRepository : JpaRepository<PerformanceScheduleEntity, Long> {
    fun findByPerformanceId(performanceId: Long): List<PerformanceScheduleEntity>
}
