package org.griotold.concert.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ConcertSchedule (
    concert: Concert,
    maxSeatNo: Int,
    remainingSeatNo: Int,
    concertStartAt: LocalDateTime,
    concertEndAt: LocalDateTime,
    reserveStartAt: LocalDateTime,
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "concert_schedule_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    var concert: Concert = concert
        protected set

    var maxSeatNo: Int = maxSeatNo
        protected set

    var remainingSeatNo: Int = remainingSeatNo
        protected set

    var concertStartAt: LocalDateTime = concertStartAt
        protected set

    var concertEndAt: LocalDateTime = concertEndAt
        protected set

    var reserveStartAt: LocalDateTime = reserveStartAt
        protected set

    var isAvailable: Boolean = true
        protected set


    fun checkAndSetAvailability() {
        if (remainingSeatNo == 0) {
            isAvailable = false
        }
    }
}