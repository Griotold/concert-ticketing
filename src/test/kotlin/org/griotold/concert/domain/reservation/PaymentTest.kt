package org.griotold.concert.domain.reservation

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.common.type.PaymentStatus
import org.junit.jupiter.api.Test

class PaymentTest {

    @Test
    fun `결제를 생성한다`() {
        // given
        val reservationId = 1L
        val price = 10000

        // when
        val payment = Payment.pay(reservationId, price)

        // then
        assertThat(payment.paymentId).isEqualTo(0)
        assertThat(payment.reservationId).isEqualTo(1L)
        assertThat(payment.price).isEqualTo(10000)
        assertThat(payment.status).isEqualTo(PaymentStatus.COMPLETE)
    }
}