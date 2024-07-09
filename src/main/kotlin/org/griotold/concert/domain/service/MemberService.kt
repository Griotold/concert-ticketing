package org.griotold.concert.domain.service

import org.griotold.concert.domain.repository.MemberRepository
import org.springframework.stereotype.Service

/**
 * 회원 조회 - 대기열 발급 하기 전에 유효한 회원인지
 * */
@Service
class MemberService (
    private val memberRepository: MemberRepository
){
}