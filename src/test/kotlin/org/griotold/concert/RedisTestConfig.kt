package org.griotold.concert

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Profile("test")
@Configuration
class RedisTestConfig (
    @Value("\${spring.data.redis.port}")
    private val redisPort: Int
) {
    private val host = "localhost"
    private var port = redisPort

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }
}