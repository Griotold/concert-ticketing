package org.griotold.concert.infra.db.user

import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserReader
import org.springframework.stereotype.Repository

@Repository
class UserReaderImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserReader {

    override fun getUser(userId: Long): User? {
        return userJpaRepository.findById(userId).orElse(null)
            ?.toDomain()
    }
}
