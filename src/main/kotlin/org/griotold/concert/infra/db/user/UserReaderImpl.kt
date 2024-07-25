package org.griotold.concert.infra.db.user

import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserReader
import org.springframework.stereotype.Repository

@Repository
class UserReaderImpl(
    private val userJpaRepository: UserJpaRepository,
    private val userCustomRepositoryImpl: UserCustomRepositoryImpl
) : UserReader {

    override fun getUser(userId: Long): User? {
        return userJpaRepository.findById(userId).orElse(null)
            ?.toDomain()
    }

    override fun getUserWithLock(userId: Long): User? {
        return userCustomRepositoryImpl.findByIdWithLock(userId)?.toDomain()
    }
}
