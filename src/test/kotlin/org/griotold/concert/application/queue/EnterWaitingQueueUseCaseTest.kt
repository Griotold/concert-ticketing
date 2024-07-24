package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.infra.db.queue.QueueEntity
import org.griotold.concert.infra.db.queue.QueueJpaRepository
import org.junit.jupiter.api.Test
import java.util.*

class EnterWaitingQueueUseCaseTest(
    private val sut: EnterWaitingQueueUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `대기열에 입장한다`() {
        // given
        val queue = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.save(queue)

        // when
        val result = sut()

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(2)
        assertThat(result.queueId).isEqualTo(2L)
        assertThat(result.rank).isEqualTo(1)
        assertThat(result.token).isNotNull()
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }
}