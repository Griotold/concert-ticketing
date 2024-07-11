package org.griotold.concert.domain.entity

import jakarta.persistence.*
import org.griotold.concert.domain.type.ProgressStatus
import java.time.LocalDateTime
import java.util.*

@Entity
class Queue (
    member: Member,
    token: String,
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
    var status: ProgressStatus = ProgressStatus.WAIT
        protected set

    var waitingNo: Int = 0
        protected set

    var createdAt : LocalDateTime = LocalDateTime.now()
        protected set

    var enteredAt : LocalDateTime? = null
       protected set

    fun updateWaitingNo(waitingNo: Int) {
        this.waitingNo = waitingNo
    }

    // 토큰이 ONGOING 상태가 되면 갱신
    fun changeOngoingStatus() {
        this.status = ProgressStatus.ONGOING
        this.enteredAt = LocalDateTime.now()
    }

}