package org.griotold.concert.domain.entity

import jakarta.persistence.*
import org.griotold.concert.domain.type.TransactionType
import java.time.LocalDateTime

@Entity
class PointHistory (
    member: Member,
    transactionType: TransactionType,
    amount: Int,
    createdAt: LocalDateTime,
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_history_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = member
        protected set

    @Enumerated(EnumType.STRING)
    var transactionType: TransactionType = transactionType
        protected set

    var amount: Int = amount
        protected set

    var createdAt : LocalDateTime = createdAt
        protected set
}