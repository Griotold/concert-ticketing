package org.griotold.concert.application.facade

import org.griotold.concert.domain.entity.Queue
import org.griotold.concert.domain.service.MemberService
import org.griotold.concert.domain.service.QueueService
import org.springframework.stereotype.Component

@Component
class QueueFacade (
    private val queueService: QueueService,
    private val memberService: MemberService,
){
    /**
     * 대기열 토큰 발급
     * */
    fun issue(memberId: Long) : Queue {
        // 1. 유효한 회원인지
        val member = memberService.findMemberById(memberId)

        // 2. 대기열에 토큰 생성
        val queue = queueService.issue(member)

        // 3. 대기순서를 업데이트
        return queueService.updateWaitingNo(queue)
    }

    /**
     * 대기열 조회
     * */
    fun retrieve(token: String) : Queue {
        // 1. 토큰으로 조회
        val queue = queueService.retrieve(token)

        // 대기순서 업데이트
        return queueService.updateWaitingNo(queue)
    }
}