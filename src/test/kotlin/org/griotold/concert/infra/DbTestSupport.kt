package org.griotold.concert.infra

import org.griotold.concert.DbCleanup
import org.griotold.concert.infra.db.config.JpaConfig
import org.griotold.concert.infra.db.config.QueryDslConfig
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(JpaConfig::class, DbCleanup::class, QueryDslConfig::class)
class DbTestSupport {

    @Autowired
    lateinit var dbCleanup: DbCleanup

    @BeforeEach
    fun setup() {
        dbCleanup.execute()
    }
}