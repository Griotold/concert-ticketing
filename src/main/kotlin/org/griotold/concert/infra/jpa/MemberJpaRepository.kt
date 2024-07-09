package org.griotold.concert.infra.jpa

import org.griotold.concert.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<Member, Long> {
}