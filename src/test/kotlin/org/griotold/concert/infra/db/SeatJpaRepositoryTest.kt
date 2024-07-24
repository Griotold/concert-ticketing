package org.griotold.concert.infra.db

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.griotold.concert.domain.common.type.SeatStatus
import org.griotold.concert.infra.DbTestSupport
import org.griotold.concert.infra.db.performance.SeatEntity
import org.griotold.concert.infra.db.performance.SeatJpaRepository
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

class SeatJpaRepositoryTest(
    private val seatJpaRepository: SeatJpaRepository,
) : DbTestSupport() {
    @Test
    fun `seatId 리스트로 좌석 상태를 변경한다`() {
        // given
        seatJpaRepository.save(SeatEntity(performanceScheduleId = 1L, seatNo = 10, price = 10000, SeatStatus.RESERVED))
        seatJpaRepository.save(SeatEntity(performanceScheduleId = 1L, seatNo = 11, price = 12000, SeatStatus.RESERVED))
        seatJpaRepository.save(SeatEntity(performanceScheduleId = 1L, seatNo = 12, price = 15000, SeatStatus.RESERVED))

        val seatIds = listOf(1L, 2L)
        val status = SeatStatus.OPEN

        // when
        seatJpaRepository.updateStatus(seatIds, status)

        // then
        val result = seatJpaRepository.findAll()

        assertThat(result[0].status).isEqualTo(SeatStatus.OPEN)
        assertThat(result[1].status).isEqualTo(SeatStatus.OPEN)
        assertThat(result[2].status).isEqualTo(SeatStatus.RESERVED)
    }
}
