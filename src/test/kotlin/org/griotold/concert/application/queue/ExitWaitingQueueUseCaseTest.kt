package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.infra.db.queue.QueueEntity
import org.griotold.concert.infra.db.queue.QueueJpaRepository
import org.junit.jupiter.api.Test
import java.util.*

class ExitWaitingQueueUseCaseTest(
    private val sut: ExitWaitingQueueUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `대기열 퇴장`() {
        // given
        val token = UUID.randomUUID().toString()
        queueJpaRepository.save(QueueEntity(token = token, status = QueueStatus.WAITING))

        // when
        sut(token)

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(0)
    }
}