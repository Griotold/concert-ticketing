package org.griotold.concert.infra.db.reservation

import jakarta.persistence.LockModeType
import org.griotold.concert.domain.common.type.ReservationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReservationEntity r where r.id=:reservationId")
    fun findByIdForUpdate(reservationId: Long): ReservationEntity?

    fun findAllByStatusAndExpiredAtBefore(status: ReservationStatus, date: LocalDateTime): List<ReservationEntity>

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update ReservationEntity r set r.status=:status where r.id in :ids")
    fun updateStatus(ids: List<Long>, status: ReservationStatus)
}
