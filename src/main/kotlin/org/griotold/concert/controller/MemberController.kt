package org.griotold.concert.controller

import org.griotold.concert.controller.dto.ChargeRequest
import org.griotold.concert.controller.dto.ChargeResponse
import org.griotold.concert.controller.dto.PointResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController {

    @PutMapping("/charge/{memberId}")
    fun charge(
        @PathVariable memberId: Long,
        @RequestBody req: ChargeRequest
    ): ResponseEntity<ChargeResponse> {
        // 하드코딩된 값으로 ChargeResponse 객체 생성
        val response = ChargeResponse(
            memberId = memberId,
            transactionType = "CHARGE",
            amount = req.amount
        )

        // ResponseEntity.ok() 메서드를 사용하여 응답 생성 및 반환
        return ResponseEntity.ok(response)
    }

    @GetMapping("/points/{memberId}")
    fun getPoints(
        @PathVariable memberId: Long,
    ): ResponseEntity<PointResponse> {
        // 하드코딩된 값으로 ChargeResponse 객체 생성
        val response = PointResponse(
            memberId = memberId,
            points = 5000
        )

        // ResponseEntity.ok() 메서드를 사용하여 응답 생성 및 반환
        return ResponseEntity.ok(response)
    }
}