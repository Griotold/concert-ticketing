package org.griotold.concert.domain.service

import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.griotold.concert.domain.entity.Member
import org.griotold.concert.domain.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class MemberServiceTest {
    @Mock
    private lateinit var memberRepository: MemberRepository

    @InjectMocks
    private lateinit var memberService: MemberService

    @Nested
    @DisplayName("회원 조회")
    inner class RetrieveTest {

        @Test
        fun `회원이 존재한다면 조회 성공`() {
            // given
            val memberId = 1L
            val member = Member(name = "angela", point = 5000)
            `when`(memberRepository.findById(memberId)).thenReturn(member)

            // when
            val result = memberService.findMemberById(memberId)

            // then
            assertThat(result).isNotNull
            assertThat(result).isEqualTo(member)
            verify(memberRepository, times(1)).findById(memberId)
        }

        @Test
        fun `존재하지 안다면 예외 발생`() {
            // given
            val memberId = 1L
            `when`(memberRepository.findById(memberId)).thenReturn(null)

            // when & then
            assertThatThrownBy {
                memberService.findMemberById(memberId)
            }.isInstanceOf(EntityNotFoundException::class.java)
                .hasMessage("Member Not Found")

            verify(memberRepository, times(1)).findById(memberId)
        }

    }
}