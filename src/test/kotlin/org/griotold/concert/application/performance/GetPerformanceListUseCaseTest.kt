package org.griotold.concert.application.performance

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.griotold.concert.application.IntegrationTestSupport
import org.griotold.concert.domain.common.Pageable
import org.griotold.concert.infra.db.performance.PerformanceEntity
import org.griotold.concert.infra.db.performance.PerformanceJpaRepository
import org.junit.jupiter.api.Test

class GetPerformanceListUseCaseTest(
    private val sut: GetPerformanceListUseCase,
    private val performanceJpaRepository: PerformanceJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `공연 정보 리스트를 조회한다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceJpaRepository.save(PerformanceEntity("공연2", "내용2"))
        performanceJpaRepository.save(PerformanceEntity("공연3", "내용3"))

        val pageable = Pageable(1, 2)

        // when
        val result = sut(pageable)

        // then
        val totalElements = performanceJpaRepository.count()

        assertThat(result.list).hasSize(2)
            .extracting("performanceId", "title", "content")
            .containsExactlyInAnyOrder(
                tuple(1L, "공연1", "내용1"),
                tuple(2L, "공연2", "내용2"),
            )
        assertThat(result.pageInfo.pageNo).isEqualTo(1)
        assertThat(result.pageInfo.pageSize).isEqualTo(2)
        assertThat(result.pageInfo.totalElements).isEqualTo(totalElements)
    }

    @Test
    fun `공연 정보가 없으면 빈 리스트를 응답한다`() {
        // given
        val pageable = Pageable(1, 2)

        // when
        val result = sut(pageable)

        // then
        val totalElements = performanceJpaRepository.count()

        assertThat(result.list).isEmpty()
        assertThat(result.pageInfo.pageNo).isEqualTo(1)
        assertThat(result.pageInfo.pageSize).isEqualTo(2)
        assertThat(result.pageInfo.totalElements).isEqualTo(totalElements)
    }
}