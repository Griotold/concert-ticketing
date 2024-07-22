package org.griotold.concert.infra.db.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.griotold.concert.domain.common.type.TransactionType
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPointHistory
import org.griotold.concert.infra.db.BaseEntity

@Entity
@Table(name = "point_history")
class PointHistoryEntity(
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    val amount: Int,
) : BaseEntity() {

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
