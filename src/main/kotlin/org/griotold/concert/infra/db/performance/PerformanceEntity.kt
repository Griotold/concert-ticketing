package org.griotold.concert.infra.db.performance

import jakarta.persistence.*
import org.griotold.concert.domain.performance.Performance
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "performance")
@EntityListeners(AuditingEntityListener::class)
class PerformanceEntity(
    val title: String,
    val content: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

    fun toDomain(): Performance {
        return Performance(
            performanceId = id!!,
            title = title,
            content = content
        )
    }
}
