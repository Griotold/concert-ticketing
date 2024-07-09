package org.griotold.concert.domain.entity

import jakarta.persistence.*
import org.griotold.concert.domain.type.PaymentStatus
import org.griotold.concert.domain.type.PaymentType
import java.time.LocalDateTime

@Entity
class Payment (
    reservation: Reservation,
    amount: Int,
    status: PaymentStatus,
    paymentType: PaymentType,
    createdAt: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    var reservation: Reservation = reservation
        protected set

    var amount: Int = amount
        protected set

    @Enumerated(EnumType.STRING)
    var status: PaymentStatus = status
        protected set

    @Enumerated(EnumType.STRING)
    var paymentType: PaymentType = paymentType
        protected set

    var createdAt : LocalDateTime = createdAt
        protected set
}