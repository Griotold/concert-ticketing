package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.infra.db.queue.QueueEntity
import org.griotold.concert.infra.db.queue.QueueJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import java.util.*

class ExitWaitingQueueUseCaseTest(
    private val sut: ExitWaitingQueueUseCase,
    private val redisTemplate: RedisTemplate<String, String>,

    ) : IntegrationTestSupport() {

    @Test
    fun `대기열 퇴장`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        val score = System.currentTimeMillis().toDouble()
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score)
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score + 1)
        redisTemplate.opsForZSet().add(waitQueueKey, token, score + 2)

        // when
        sut(token)

        // then
        val count = redisTemplate.opsForZSet().size(waitQueueKey)
        assertThat(count).isEqualTo(2)
    }
}