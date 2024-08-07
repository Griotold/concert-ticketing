package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.infra.db.queue.QueueEntity
import org.griotold.concert.infra.db.queue.QueueJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import java.util.*

class EnterWaitingQueueUseCaseTest(
    private val sut: EnterWaitingQueueUseCase,
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTestSupport() {

    @Test
    fun `대기열에 입장한다`() {
        // given
        val waitQueueKey = "queue:wait"

        // when
        val result = sut()

        // then
        val count = redisTemplate.opsForZSet().size(waitQueueKey)
        assertThat(count).isEqualTo(1)

        assertThat(result.rank).isEqualTo(1)
        assertThat(result.token).isNotNull()
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
    }
}