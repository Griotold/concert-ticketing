package org.griotold.concert.domain.repository

import org.griotold.concert.domain.entity.Member

interface MemberRepository {

    fun findById(id: Long): Member?

    fun save(member: Member): Member

}