package org.griotold.concert.domain.service

import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.griotold.concert.domain.component.TokenGenerator
import org.griotold.concert.domain.entity.Member
import org.griotold.concert.domain.entity.Queue
import org.griotold.concert.domain.repository.QueueRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
class QueueServiceTest {

    @Mock
    private lateinit var queueRepository: QueueRepository

    @Mock
    private lateinit var tokenGenerator: TokenGenerator

    @InjectMocks
    private lateinit var queueService: QueueService

    @Nested
    @DisplayName("대기열 발급")
    inner class IssueTest {
        @Test
        fun `유효한 멤버인 경우 대기열 발급 성공`() {
            val memberId = 0L
            val member = Member(name = "angela", point = 5000)
            val queue = Queue(member = member, token = "test token")

            `when`(tokenGenerator.generateToken(memberId)).thenReturn("test token")
            `when`(queueRepository.save(any())).thenReturn(queue)

            val issuedQueue = queueService.issue(member)

            assertThat(issuedQueue).isNotNull
            assertThat(issuedQueue.member).isEqualTo(member)
            verify(queueRepository, times(1)).save(any())
        }
    }

    @Nested
    @DisplayName("대기순서 갱신")
    inner class UpdateWaitingNoTest {
        @Test
        fun `가장 최근에 들어간 토큰의 id를 조회해서 대기순서 갱신`() {
            val member = Member(name = "angela", point = 5000)
            val queue = Queue(member = member, token = "test token")
            queue.id = 10L
            val lastTokenId = 5L

            `when`(queueRepository.getLastTokenId(any())).thenReturn(lastTokenId)

            val updatedQueue = queueService.updateWaitingNo(queue)

            assertThat(updatedQueue).isNotNull
            assertThat(updatedQueue.waitingNo).isEqualTo((queue.id!! - lastTokenId).toInt())
            verify(queueRepository, times(1)).getLastTokenId(any())
        }
    }


    @Nested
    @DisplayName("대기열 조회")
    inner class RetrieveTest {
        @Test
        fun `토큰으로 대기열을 조회하면 성공적으로 조회되어야 한다`() {
            val member = Member(name = "angela", point = 5000)
            val queue = Queue(member = member, token = "test token")
            val token = queue.token

            `when`(queueRepository.findByToken(any())).thenReturn(queue)

            val retrievedQueue = queueService.retrieve(token)

            assertThat(retrievedQueue).isNotNull
            assertThat(retrievedQueue.member).isEqualTo(member)
            verify(queueRepository, times(1)).findByToken(any())
        }

        @Test
        fun `잘못된 토큰으로 대기열을 조회하면 예외를 던져야 한다`() {
            val invalidToken = "invalidToken"

            `when`(queueRepository.findByToken(invalidToken)).thenReturn(null)

            assertThatThrownBy {
                queueService.retrieve(invalidToken)
            }.isInstanceOf(EntityNotFoundException::class.java)
                .hasMessage("Not Found")

            verify(queueRepository, times(1)).findByToken(invalidToken)
        }
    }
}