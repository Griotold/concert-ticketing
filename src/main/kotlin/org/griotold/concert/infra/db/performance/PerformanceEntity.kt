package org.griotold.concert.infra.db.performance

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.griotold.concert.domain.performance.Performance
import org.griotold.concert.infra.db.BaseEntity

@Entity
@Table(name = "performance")
class PerformanceEntity(
    val title: String,
    val content: String
) : BaseEntity() {

    fun toDomain(): Performance {
        return Performance(
            performanceId = id!!,
            title = title,
            content = content
        )
    }
}
