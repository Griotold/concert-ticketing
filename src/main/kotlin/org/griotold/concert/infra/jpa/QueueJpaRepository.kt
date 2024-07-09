package org.griotold.concert.infra.jpa

import org.griotold.concert.domain.entity.Queue
import org.springframework.data.jpa.repository.JpaRepository

interface QueueJpaRepository : JpaRepository<Queue, Long> {
}