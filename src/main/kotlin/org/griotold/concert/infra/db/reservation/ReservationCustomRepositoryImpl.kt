package org.griotold.concert.infra.db.reservation

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.griotold.concert.domain.common.type.ReservationStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ReservationCustomRepositoryImpl(
    private val query: JPAQueryFactory,
    private val em: EntityManager,
) : ReservationCustomRepository {

    private val reservation = QReservationEntity.reservationEntity

    @Transactional
    override fun findByIdForUpdate(reservationId: Long): ReservationEntity? {
        return query
            .selectFrom(reservation)
            .where(reservation.id.eq(reservationId))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetchOne()
    }

    @Transactional
    override fun updateStatus(ids: List<Long>, status: ReservationStatus) {
        query
            .update(reservation)
            .set(reservation.status, status)
            .where(reservation.id.`in`(ids))
            .execute()

        em.flush()
        em.clear()
    }
}
