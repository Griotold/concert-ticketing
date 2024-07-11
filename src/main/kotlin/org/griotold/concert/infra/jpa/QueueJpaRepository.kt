package org.griotold.concert.infra.jpa

import org.griotold.concert.domain.entity.Queue
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QueueJpaRepository : JpaRepository<Queue, Long> {


    @Query("""
        SELECT q.id FROM Queue q
        WHERE q.id < : id
        AND q.status = 'ONGOING'
        ORDER BY q.enteredAt DESC
    """)
    fun getLastTokenId(id: Long, pageable: Pageable): List<Long>

    fun findByToken(token: String): Queue?
}