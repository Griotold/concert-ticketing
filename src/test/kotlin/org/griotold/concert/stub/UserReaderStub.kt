package org.griotold.concert.stub

import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserPoint
import org.griotold.concert.domain.user.UserReader

class UserReaderStub : UserReader{
    override fun getUser(userId: Long): User {
        return User(1L, "유저1", UserPoint(10000))
    }
}