package org.griotold.concert.domain.queue

interface QueueReader {

    fun getQueueStatus(token: String): WaitingQueue?

    fun getActiveCount(): Int
}