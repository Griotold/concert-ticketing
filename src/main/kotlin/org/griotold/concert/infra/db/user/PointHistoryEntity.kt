package org.griotold.concert.infra.db.user

import jakarta.persistence.*
import org.griotold.concert.domain.common.type.TransactionType
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPointHistory
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "point_history")
@EntityListeners(AuditingEntityListener::class)
class PointHistoryEntity(
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    val amount: Int,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

    companion object {

        fun of(user: User, userPointHistory: UserPointHistory): PointHistoryEntity {
            return PointHistoryEntity(
                userId = user.userId,
                type = userPointHistory.type,
                amount = userPointHistory.amount
            )
        }
    }

    fun toDomain(): UserPointHistory {
        return UserPointHistory(
            amount = amount,
            type = type
        )
    }
}
