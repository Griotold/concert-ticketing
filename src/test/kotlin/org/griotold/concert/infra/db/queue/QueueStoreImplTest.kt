package org.griotold.concert.infra.db.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.domain.queue.QueueStore
import org.griotold.concert.domain.queue.WaitingQueue
import org.griotold.concert.infra.DbTestSupport
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import java.time.LocalDateTime
import java.util.*

@Import(QueueStoreImpl::class)
class QueueStoreImplTest(
    private val sut: QueueStore,
    private val queueJpaRepository: QueueJpaRepository,
) : DbTestSupport() {

    @Test
    fun `대기열 입장`() {
        // given
        val token = UUID.randomUUID().toString()
        val queue = WaitingQueue(
            token = token,
            rank = 0,
            status = QueueStatus.WAITING,
        )

        // when
        val result = sut.enter(queue)

        // then
        assertThat(result.queueId).isEqualTo(1L)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.rank).isEqualTo(0)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }

    @Test
    fun `대기열 퇴장`() {
        // given
        val token = UUID.randomUUID().toString()
        queueJpaRepository.save(QueueEntity(token = token, status = QueueStatus.WAITING))

        // when
        sut.exit(token)

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(0)
    }

    @Test
    fun `만료시간이 지난 토큰을 삭제한다`() {
        // given
        val queue1 = QueueEntity(
            token = UUID.randomUUID().toString(),
            status = QueueStatus.PROCEEDING,
            expiredAt = LocalDateTime.now().minusMinutes(1)
        )
        val queue2 = QueueEntity(
            token = UUID.randomUUID().toString(),
            status = QueueStatus.PROCEEDING,
            expiredAt = LocalDateTime.now().minusMinutes(1)
        )
        val queue3 = QueueEntity(
            token = UUID.randomUUID().toString(),
            status = QueueStatus.WAITING
        )
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3))

        // when
        sut.clearExpiredToken()

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(1)
    }

    @Test
    fun `id 리스트의 토큰을 진행 상태로 변경한다`() {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3))

        val targetCount = 2

        // when
        sut.activeTokens(targetCount)

        val list = queueJpaRepository.findAll()
        assertThat(list[0].status).isEqualTo(QueueStatus.PROCEEDING)
        assertThat(list[1].status).isEqualTo(QueueStatus.PROCEEDING)
        assertThat(list[2].status).isEqualTo(QueueStatus.WAITING)
    }
}