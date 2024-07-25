package org.griotold.concert.application

import org.griotold.concert.DbCleanup
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@SpringBootTest
class IntegrationTestSupport {

    @Autowired
    lateinit var dbCleanup: DbCleanup

//    @Autowired
//    lateinit var redisConnectionFactory: RedisConnectionFactory

    @BeforeEach
    fun setup() {
        dbCleanup.execute()
//        redisConnectionFactory.connection.commands().flushAll()
    }
}