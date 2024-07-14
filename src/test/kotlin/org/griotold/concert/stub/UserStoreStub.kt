package org.griotold.concert.stub

import org.griotold.concert.domain.user.*

class UserStoreStub : UserStore{
    override fun saveUser(user: User): User {
        return user
    }

    override fun savePointHistory(user: User, userPointHistory: UserPointHistory): UserPointHistory {
        return userPointHistory
    }

}