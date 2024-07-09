package org.griotold.concert.domain.entity

import jakarta.persistence.*
import org.griotold.concert.domain.type.SeatStatus
import java.time.LocalDateTime

@Entity
class Seat (
    concertSchedule: ConcertSchedule,
    seatNo: Int,
    status: SeatStatus,
    price: Int,
    reservedAt: LocalDateTime
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_id")
    var concertSchedule: ConcertSchedule = concertSchedule
        protected set

    var seatNo: Int = seatNo
        protected set

    @Enumerated(EnumType.STRING)
    var status: SeatStatus = status
        protected set

    var price: Int = price
        protected set

    var reservedAt: LocalDateTime = reservedAt
}