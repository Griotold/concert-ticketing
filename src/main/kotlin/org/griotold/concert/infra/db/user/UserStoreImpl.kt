package org.griotold.concert.infra.db.user

import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPointHistory
import org.griotold.concert.domain.user.UserStore
import org.springframework.stereotype.Repository

@Repository
class UserStoreImpl (
    private val userJpaRepository: UserJpaRepository,
    private val pointHistoryJpaRepository: PointHistoryJpaRepository
) : UserStore {

    override fun saveUser(user: User): User {
        val userEntity = UserEntity.of(user)
        return userJpaRepository.save(userEntity).toDomain()
    }

    override fun savePointHistory(user: User, userPointHistory: UserPointHistory): UserPointHistory {
        val pointHistoryEntity = PointHistoryEntity.of(user, userPointHistory)
        return pointHistoryJpaRepository.save(pointHistoryEntity).toDomain()
    }

}