package org.griotold.concert.domain.queue

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.common.type.QueueStatus
import org.junit.jupiter.api.Test
import java.util.*

class WaitingQueueTest {

    @Test
    fun `대기열 입장`() {
        // given
        val token = UUID.randomUUID().toString()

        // when
        val result = WaitingQueue.enter(token)

        // then
        assertThat(result.queueId).isEqualTo(0)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.rank).isEqualTo(0)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }
}
