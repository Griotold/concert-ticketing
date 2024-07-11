package org.griotold.concert.domain.repository

import org.griotold.concert.domain.entity.Queue

interface QueueRepository {

    fun findById(id: Long): Queue?

    fun save(queue: Queue): Queue

    fun delete(queue: Queue)

    fun getLastTokenId(id: Long): Long

    fun findByToken(token: String): Queue?
}