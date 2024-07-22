package org.griotold.concert.application.performance

import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.infra.db.performance.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDateTime

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class GetAvailableSeatListUseCaseTest(
    private val sut: GetAvailableSeatListUseCase,
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) {

    @Test
    fun `예약 가능한 좌석 리스트를 조회한다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceScheduleJpaRepository.save(
            PerformanceScheduleEntity(
                1L,
                LocalDateTime.of(2024, 7, 15, 17, 0, 0),
                LocalDateTime.of(2024, 7, 15, 20, 0, 0),
                LocalDateTime.of(2024, 7, 1, 17, 0, 0)
            )
        )
        seatJpaRepository.save(SeatEntity(1L, 1, 10000, SeatStatus.RESERVED))
        seatJpaRepository.save(SeatEntity(1L, 2, 20000, SeatStatus.OPEN))

        val performanceScheduleId = 1L

        // when
        val result = sut(performanceScheduleId)

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].seatId).isEqualTo(2)
        assertThat(result[0].seatNo).isEqualTo(2)
        assertThat(result[0].price).isEqualTo(20000)
    }
}
