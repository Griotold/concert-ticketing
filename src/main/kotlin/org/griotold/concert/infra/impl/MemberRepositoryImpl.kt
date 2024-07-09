package org.griotold.concert.infra.impl

import org.griotold.concert.domain.entity.Member
import org.griotold.concert.domain.repository.MemberRepository
import org.griotold.concert.infra.jpa.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository
) : MemberRepository {

    override fun findById(id: Long): Member? {
        return memberJpaRepository.findByIdOrNull(id)
    }

    override fun save(member: Member): Member {
        return memberJpaRepository.save(member)
    }
}