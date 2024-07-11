package org.griotold.concert.application.facade

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.entity.Member
import org.griotold.concert.domain.entity.Queue
import org.griotold.concert.domain.service.MemberService
import org.griotold.concert.domain.service.QueueService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThatThrownBy

@ExtendWith(MockitoExtension::class)
class QueueFacadeTest {

    @Mock
    private lateinit var queueService: QueueService

    @Mock
    private lateinit var memberService: MemberService

    @InjectMocks
    private lateinit var queueFacade: QueueFacade

    @Nested
    @DisplayName("대기열 토큰 발급")
    inner class IssueTest {

        @Test
        fun `유효한 회원인 경우 대기열 토큰 발급 성공`() {
            val memberId = 1L
            val member = Member(name = "angela", point = 5000)
            val queue = Queue(member = member, token = "test token")
            queue.id = 10L

            `when`(memberService.findMemberById(memberId)).thenReturn(member)
            `when`(queueService.issue(member)).thenReturn(queue)
            `when`(queueService.updateWaitingNo(queue)).thenReturn(queue)

            val issuedQueue = queueFacade.issue(memberId)

            assertThat(issuedQueue).isNotNull
            assertThat(issuedQueue.member).isEqualTo(member)
            assertThat(issuedQueue.token).isEqualTo("test token")
            verify(memberService, times(1)).findMemberById(memberId)
            verify(queueService, times(1)).issue(member)
            verify(queueService, times(1)).updateWaitingNo(queue)
        }
    }

    @Nested
    @DisplayName("대기열 조회")
    inner class RetrieveTest {

        @Test
        fun `유효한 토큰으로 대기열 조회 성공`() {
            val member = Member(name = "angela", point = 5000)
            val queue = Queue(member = member, token = "test token")
            queue.id = 10L

            `when`(queueService.retrieve("test token")).thenReturn(queue)
            `when`(queueService.updateWaitingNo(queue)).thenReturn(queue)

            val retrievedQueue = queueFacade.retrieve("test token")

            assertThat(retrievedQueue).isNotNull
            assertThat(retrievedQueue.member).isEqualTo(member)
            verify(queueService, times(1)).retrieve("test token")
            verify(queueService, times(1)).updateWaitingNo(queue)
        }

        @Test
        fun `잘못된 토큰으로 대기열 조회 실패`() {
            val invalidToken = "invalidToken"

            `when`(queueService.retrieve(invalidToken)).thenThrow(EntityNotFoundException("Not Found"))

            assertThatThrownBy {
                queueService.retrieve(invalidToken)
            }.isInstanceOf(EntityNotFoundException::class.java)
                .hasMessage("Not Found")

            verify(queueService, times(1)).retrieve(invalidToken)
        }
    }
}
