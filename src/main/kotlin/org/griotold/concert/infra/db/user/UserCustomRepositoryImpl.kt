package org.griotold.concert.infra.db.user

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.LockModeType
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl (
    private val query: JPAQueryFactory,
){

    private val user = QUserEntity.userEntity

    fun findByIdWithLock(userId: Long) :UserEntity? {
        return query
            .selectFrom(user)
            .where(user.id.eq(userId))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetchOne()
    }

}