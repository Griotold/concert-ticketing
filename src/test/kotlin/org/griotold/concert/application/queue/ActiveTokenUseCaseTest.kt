package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.IntegrationTestSupport
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import java.util.*

class ActiveTokenUseCaseTest(
    private val sut: ActiveTokenUseCase,
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTestSupport() {

    @Test
    fun `만료된 토큰을 삭제하고 대기 상태의 토큰을 활성화 한다`() {
        // given
        val waitQueueKey = "queue:wait"
        val proceedQueueKey = "queue:proceed:*"


        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())

        val maxUserCount = 3

        // when
        sut(maxUserCount)

        // then
        val waitingCount = redisTemplate.opsForZSet().size(waitQueueKey)
        val activeCount = redisTemplate.keys(proceedQueueKey).count()

        assertThat(waitingCount).isEqualTo(2)
        assertThat(activeCount).isEqualTo(3)
    }
}