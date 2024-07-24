package org.griotold.concert.infra.db.queue

import jakarta.persistence.*
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.WaitingQueue
import org.griotold.concert.infra.db.common.Audit
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "queue")
@EntityListeners(AuditingEntityListener::class)
class QueueEntity(
    @Column(unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    val status: QueueStatus,
    val expiredAt: LocalDateTime? = null,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    companion object {

        fun of(waitingQueue: WaitingQueue): QueueEntity {
            return QueueEntity(
                token = waitingQueue.token,
                status = waitingQueue.status,
                expiredAt = waitingQueue.expiredAt,
            ).apply { id = waitingQueue.queueId }
        }
    }

    fun toDomain(rank: Int): WaitingQueue {
        return WaitingQueue(
            queueId = id!!,
            token = token,
            rank = rank,
            status = status,
            expiredAt = expiredAt,
        )
    }
}