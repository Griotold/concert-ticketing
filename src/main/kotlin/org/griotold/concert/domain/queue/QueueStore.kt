package org.griotold.concert.domain.queue

interface QueueStore {

    fun enter(waitingQueue: WaitingQueue): WaitingQueue

    fun exit(token: String)

    fun clearExpiredToken()

    fun activeTokens(targetCount: Int)
}