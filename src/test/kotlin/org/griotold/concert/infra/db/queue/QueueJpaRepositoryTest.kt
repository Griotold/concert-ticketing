package org.griotold.concert.infra.db.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.common.type.QueueStatus
import org.griotold.concert.infra.DbTestSupport
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime
import java.util.*

class QueueJpaRepositoryTest(
    private val queueJpaRepository: QueueJpaRepository,
) : DbTestSupport() {

    @ParameterizedTest
    @CsvSource("1,0", "2,0", "3,1", "4,2", "5,3")
    fun `대기 순번을 조회 할 수 있다`(queueId: Long, expected: Long) {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue4 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue5 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3, queue4, queue5))

        // when
        val rank = queueJpaRepository.rank(queueId)

        // then
        assertThat(rank).isEqualTo(expected)
    }

    @Test
    fun `대기 상태 토큰을 조회한다`() {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue4 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue5 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3, queue4, queue5))

        val pageable = PageRequest.of(0, 3)

        // when
        val result = queueJpaRepository.findIdByStatusOrderById(QueueStatus.WAITING, pageable)

        // then
        assertThat(result).hasSize(3)
        assertThat(result).isEqualTo(listOf(2L, 3L, 4L))
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
        val result = queueJpaRepository.deleteByExpiredAtBefore(LocalDateTime.now())

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(1)

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `id 리스트로 상태를 변경한다`() {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3))

        val targetIds = listOf(1L, 2L)
        val expiredAt = LocalDateTime.now().plusMinutes(5)

        // when
        val result = queueJpaRepository.updateStatusByIds(targetIds, QueueStatus.PROCEEDING, expiredAt)
        assertThat(result).isEqualTo(2)

        val list = queueJpaRepository.findAll()
        assertThat(list[0].status).isEqualTo(QueueStatus.PROCEEDING)
        assertThat(list[0].expiredAt).isNotNull()

        assertThat(list[1].status).isEqualTo(QueueStatus.PROCEEDING)
        assertThat(list[1].expiredAt).isNotNull()

        assertThat(list[2].status).isEqualTo(QueueStatus.WAITING)
        assertThat(list[2].expiredAt).isNull()
    }
}