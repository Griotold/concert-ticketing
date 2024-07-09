package org.griotold.concert.domain.entity

import jakarta.persistence.*
import org.griotold.concert.domain.type.ProgressStatus
import java.time.LocalDateTime

@Entity
class Queue (
    member: Member,
    token: String,
    status: ProgressStatus,
    waitingNo: Int,
    createdAt: LocalDateTime,
    expiresAt: LocalDateTime,
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "queue_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = member
        protected set

    var token: String = token
        protected set

    @Enumerated(EnumType.STRING)
    var status: ProgressStatus = status
        protected set

    var waitingNo: Int = waitingNo
        protected set

    var createdAt : LocalDateTime = createdAt
        protected set

    var expiresAt : LocalDateTime = expiresAt
        protected set
}