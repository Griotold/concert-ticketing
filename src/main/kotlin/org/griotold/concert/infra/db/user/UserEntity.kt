package org.griotold.concert.infra.db.user

import jakarta.persistence.*
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class UserEntity(
    val name: String,
    val point: Int,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

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
