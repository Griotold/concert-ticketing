package org.griotold.concert.controller

import org.griotold.concert.controller.dto.PayResponse
import org.griotold.concert.controller.dto.PayRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentController {

    @PostMapping("/{reservationId}")
    fun pay(
        @PathVariable reservationId: Long,
        @RequestHeader("token") token: String,
        @RequestBody req: PayRequest
    ) : ResponseEntity<PayResponse> {
        val response = PayResponse(
            paymentId = 200L,
            paymentPrice = 5000,
            status = "SUCCESS",
            paidAt = "2024-07-01T20:00:00"
        )

        return ResponseEntity.ok(response)
    }
}