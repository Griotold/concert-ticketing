package org.griotold.concert.domain.service

import jakarta.persistence.EntityNotFoundException
import org.griotold.concert.domain.entity.Member
import org.griotold.concert.domain.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 회원 조회 - 대기열 발급 하기 전에 유효한 회원인지
 * */
@Transactional(readOnly = true)
@Service
class MemberService (
    private val memberRepository: MemberRepository
){

    fun findMemberById(id: Long): Member {
        return memberRepository.findById(id) ?: throw EntityNotFoundException("Member Not Found")
    }
}