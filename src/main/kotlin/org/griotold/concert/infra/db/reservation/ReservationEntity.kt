package org.griotold.concert.infra.db.reservation

import jakarta.persistence.*
import org.griotold.concert.domain.common.type.ReservationStatus
import org.griotold.concert.domain.reservation.Reservation
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener::class)
class ReservationEntity(
    val userId: Long,
    val seatId: Long,
    val price: Int,

    @Enumerated(EnumType.STRING)
    val status: ReservationStatus,
    val expiredAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

    @Version // 엔티티의 버젼을 관리 --> 낙관적 락
    var version: Long = 0

    companion object {

        fun of(reservation: Reservation): ReservationEntity {
            return ReservationEntity(
                userId = reservation.userId,
                seatId = reservation.seatId,
                price = reservation.price,
                status = reservation.status,
                expiredAt = reservation.expiredAt,
            ).apply { id = reservation.reservationId }
        }
    }

    fun toDomain(): Reservation {
        return Reservation(
            reservationId = id!!,
            userId = userId,
            seatId = seatId,
            price = price,
            status = status,
            expiredAt = expiredAt,
        )
    }
}
