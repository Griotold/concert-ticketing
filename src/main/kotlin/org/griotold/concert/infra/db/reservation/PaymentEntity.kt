package org.griotold.concert.infra.db.reservation

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.griotold.concert.domain.common.type.PaymentStatus
import org.griotold.concert.domain.reservation.Payment
import org.griotold.concert.infra.db.BaseEntity

@Entity
@Table(name = "payment")
class PaymentEntity(
    val reservationId: Long,
    val price: Int,
    val status: PaymentStatus,
) : BaseEntity() {

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
