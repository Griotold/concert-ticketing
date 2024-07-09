package org.griotold.concert.domain.entity

import jakarta.persistence.*
import org.griotold.concert.domain.type.ReservationStatus
import java.time.LocalDateTime

@Entity
class Reservation (
    seat: Seat,
    member: Member,
    status: ReservationStatus,
    reservedAt: LocalDateTime
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    var id: Long? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    var seat: Seat = seat
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = member
        protected set

    @Enumerated(EnumType.STRING)
    var status: ReservationStatus = status
        protected set

    var reservedAt: LocalDateTime = reservedAt
        protected set
}