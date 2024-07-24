package org.griotold.concert.domain.queue

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.*

@ExtendWith(MockitoExtension::class)
class QueueServiceTest {

    @InjectMocks
    private lateinit var sut: QueueService

    @Mock
    private lateinit var queueReader: QueueReader

    @Mock
    private lateinit var queueStore: QueueStore

    @Mock
    private lateinit var tokenGenerator: TokenGenerator

    @Test
    fun `대기 상태의 토큰을 진행 상태로 변경한다`() {
        // given
        val maxUserCount = 50
        val activeUserCount = 48
        val targetCount = maxUserCount - activeUserCount

        given { queueReader.getActiveCount() }.willAnswer { activeUserCount }

        // when
        sut.activeTokens(maxUserCount)

        // then
        verify(queueReader, times(1)).getActiveCount()
        verify(queueStore, times(1)).activeTokens(targetCount)
    }

    @Test
    fun `토큰 상태를 조회 시 해당 토큰이 없으면 QueueException 이 발생한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when, then
        assertThatThrownBy { sut.getQueueStatus(token) }
            .isInstanceOf(QueueException::class.java)
            .hasMessage("토큰이 없습니다.")
    }
}