package org.griotold.concert.infra.db.reservation

import org.griotold.concert.domain.common.type.ReservationStatus

interface ReservationCustomRepository {

    fun findByIdForUpdate(reservationId: Long): ReservationEntity?

    fun updateStatus(ids: List<Long>, status: ReservationStatus)
}
