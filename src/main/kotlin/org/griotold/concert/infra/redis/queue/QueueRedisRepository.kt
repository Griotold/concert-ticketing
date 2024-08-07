package org.griotold.concert.infra.redis.queue

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class QueueRedisRepository (
    private val redisTemplate: RedisTemplate<String, String>,
){
    private val WAIT_QUEUE_KEY = "queue:wait"
    private val PROCEED_QUEUE_KEY = "queue:proceed:"
    private val PROCEED_QUEUE_KEY_FOR_SCAN = "queue:proceed:*"

    fun getRank(token: String): Long? {
        return redisTemplate.opsForZSet().rank(WAIT_QUEUE_KEY, token)?.let { it + 1 }
    }

    fun getActiveCount(): Int {
        var cursor: Long
        var count = 0

        do {
            val keys = redisTemplate.scan(
                ScanOptions.scanOptions()
                    .match(PROCEED_QUEUE_KEY_FOR_SCAN)
                    .count(100)
                    .build()
            )
            count += keys.stream().count().toInt()
            cursor = keys.cursorId
        } while (cursor != 0L)

        return count
    }

    fun enterWaitQueue(token: String): Long {
        val timeStamp = System.currentTimeMillis()

        redisTemplate.opsForZSet()
            .add(WAIT_QUEUE_KEY, token, timeStamp.toDouble())

        return getRank(token)!!
    }

    fun exitWaitQueue(token: String) {
        redisTemplate.opsForZSet()
            .remove(WAIT_QUEUE_KEY, token)
    }

    fun activeTokens(count: Long) {
        redisTemplate.opsForZSet()
            .popMin(WAIT_QUEUE_KEY, count)
            ?.map {
                val key = PROCEED_QUEUE_KEY + it.value
                redisTemplate.opsForValue().set(key, it.value!!, Duration.ofSeconds(300))
            }
    }

    fun isProceedToken(token: String): Boolean {
        return redisTemplate.opsForValue().get(PROCEED_QUEUE_KEY + token) != null
    }
}