package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.infra.db.queue.QueueEntity
import org.griotold.concert.infra.db.queue.QueueJpaRepository
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class ActiveTokenUseCaseTest(
    private val sut: ActiveTokenUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `만료된 토큰을 삭제하고 대기 상태의 토큰을 활성화 한다`() {
        // given
        val expiredTokens = List(5) {
            QueueEntity(
                token = UUID.randomUUID().toString(),
                status = QueueStatus.PROCEEDING,
                expiredAt = LocalDateTime.now().minusMinutes(5)
            )
        }
        queueJpaRepository.saveAll(expiredTokens)

        val proceedingTokens = List(5) {
            QueueEntity(
                token = UUID.randomUUID().toString(),
                status = QueueStatus.PROCEEDING,
                expiredAt = LocalDateTime.now().plusMinutes(5)
            )
        }
        queueJpaRepository.saveAll(proceedingTokens)

        val waitingTokens = List(10) {
            QueueEntity(
                token = UUID.randomUUID().toString(),
                status = QueueStatus.WAITING
            )
        }
        queueJpaRepository.saveAll(waitingTokens)

        val maxUserCount = 10

        // when
        sut(maxUserCount)

        // then
        val totalCount = queueJpaRepository.count()
        val activeCount = queueJpaRepository.countByStatus(QueueStatus.PROCEEDING)
        val waitingCount = queueJpaRepository.countByStatus(QueueStatus.WAITING)

        assertThat(totalCount).isEqualTo(15)
        assertThat(activeCount).isEqualTo(10)
        assertThat(waitingCount).isEqualTo(5)
    }
}