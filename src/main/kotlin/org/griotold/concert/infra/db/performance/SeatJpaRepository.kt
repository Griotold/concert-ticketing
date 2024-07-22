package org.griotold.concert.infra.db.performance

import jakarta.persistence.LockModeType
import org.griotold.concert.domain.common.type.SeatStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findByPerformanceScheduleIdAndStatus(scheduleId: Long, status: SeatStatus): List<SeatEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SeatEntity s where s.id =: seatId")
    fun findByIdForUpdate(seatId: Long): SeatEntity?

    /**
     * Modfying 어노테이션은 쿼리가 데이터베이스 쓰기 작업임을 스프링 데이터 JPA에 알려주는 어노테이션
     * flushAutomatically 는 메서드가 호출 될 때 영속성 컨텍스트를 자동으로 flush 하여 데이터베이스에 즉시 반영
     * clearAutomatically 는 메서드가 호출 된 후 영속성 컨텍스트를 clear 하여 후속 작업에서 최신 상태를 반영
     * */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SeatEntity s set s.status =:status where s.id in :seatIds")
    fun updateStatus(seatIds: List<Long>, status: SeatStatus)
}
