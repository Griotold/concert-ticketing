package org.griotold.concert.infra.db.user

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.griotold.concert.infra.db.BaseEntity

@Entity
@Table(name = "users")
class UserEntity (
    val name: String,
    val point: Int,
) : BaseEntity() {

    companion object {

        fun of(user: User): UserEntity {
            return UserEntity(
                name = user.name,
                point = user.point.amount,
            )
                .apply { this.id = user.userId }
        }
    }

    fun toDomain(): User {
        return User(
            userId = id!!,
            name = name,
            UserPoint(point)
        )
    }
}