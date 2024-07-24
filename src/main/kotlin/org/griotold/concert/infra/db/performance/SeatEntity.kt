package org.griotold.concert.infra.db.performance

import jakarta.persistence.*
import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.domain.performance.Seat
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "seat")
@EntityListeners(AuditingEntityListener::class)
class SeatEntity(
    val performanceScheduleId: Long,
    val seatNo: Int,
    val price: Int,

    @Enumerated(EnumType.STRING)
    val status: SeatStatus,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

    companion object {
        fun of(seat: Seat): SeatEntity {
            return SeatEntity(
                performanceScheduleId = seat.performanceScheduleId,
                seatNo = seat.seatNo,
                price = seat.price,
                status = seat.status,
            ).apply { id = seat.seatId }
        }
    }

    fun toDomain(): Seat {
        return Seat(
            performanceScheduleId = performanceScheduleId,
            seatId = id!!,
            seatNo = seatNo,
            price = price,
            status = status,
        )
    }
}
