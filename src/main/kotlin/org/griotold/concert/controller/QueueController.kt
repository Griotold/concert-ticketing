package org.griotold.concert.controller

import org.griotold.concert.controller.dto.IssueTokenRequest
import org.griotold.concert.controller.dto.IssueTokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/queue")
class QueueController {

    @PostMapping
    fun issue(
        @RequestBody req: IssueTokenRequest
    ): ResponseEntity<IssueTokenResponse> {
        // 하드코딩된 값으로 IssueTokenResponse 객체 생성
        val response = IssueTokenResponse(
            token = "sampleToken123",
            memberId = 1L,
            waitingNum = 42
        )

        // ResponseEntity.ok() 메서드를 사용하여 응답 생성 및 반환
        return ResponseEntity.ok(response)
    }
}