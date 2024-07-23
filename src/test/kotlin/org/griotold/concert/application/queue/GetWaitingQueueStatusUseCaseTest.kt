package org.griotold.concert.application.queue

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.QueueException
import org.griotold.concert.infra.db.queue.QueueEntity
import org.griotold.concert.infra.db.queue.QueueJpaRepository
import org.junit.jupiter.api.Test
import java.util.*

class GetWaitingQueueStatusUseCaseTest(
    private val sut: GetWaitingQueueStatusUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `토큰 상태를 조회한다`() {
        // given
        queueJpaRepository.save(QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING))
        queueJpaRepository.save(QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING))

        val token = UUID.randomUUID().toString()
        queueJpaRepository.save(QueueEntity(token = token, status = QueueStatus.WAITING))

        // when
        val result = sut(token)

        // then
        assertThat(result.queueId).isEqualTo(3L)
        assertThat(result.rank).isEqualTo(2)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.expiredAt).isNull()
    }

    @Test
    fun `토큰 상태를 조회 시 해당 토큰이 없으면 QueueException 발생한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when, then
        assertThatThrownBy { sut(token) }
            .isInstanceOf(QueueException::class.java)
            .hasMessage("토큰이 없습니다.")
    }
}