package org.griotold.concert.infra.db.performance

import jakarta.persistence.*
import org.griotold.concert.domain.performance.PerformanceSchedule
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "performance_schedule")
@EntityListeners(AuditingEntityListener::class)
class PerformanceScheduleEntity(
    val performanceId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val reservationAt: LocalDateTime,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

    fun toDomain(): PerformanceSchedule {
        return PerformanceSchedule(
            performanceScheduleId = id!!,
            startAt = startAt,
            endAt = endAt,
            reservationAt = reservationAt,
        )
    }
}
