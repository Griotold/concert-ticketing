package org.griotold.concert.infra.db.reservation

import jakarta.persistence.*
import org.griotold.concert.domain.common.type.PaymentStatus
import org.griotold.concert.domain.reservation.Payment
import org.griotold.concert.infra.db.common.Audit
import org.griotold.concert.infra.db.common.SoftDeletion
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "payment")
@EntityListeners(AuditingEntityListener::class)
class PaymentEntity(
    val reservationId: Long,
    val price: Int,
    val status: PaymentStatus,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    val audit: Audit = Audit()

    @Embedded
    val softDeletion: SoftDeletion = SoftDeletion()

    companion object {

        fun of(payment: Payment): PaymentEntity {
            return PaymentEntity(
                reservationId = payment.reservationId,
                price = payment.price,
                status = payment.status,
            ).apply { id = payment.paymentId }
        }
    }

    fun toDomain(): Payment {
        return Payment(
            paymentId = id!!,
            reservationId = reservationId,
            price = price,
            status = status,
        )
    }
}
