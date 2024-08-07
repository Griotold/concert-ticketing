package org.griotold.concert.domain.user

interface UserReader {
    fun getUser(userId: Long): User?

    fun getUserWithLock(userId: Long): User?
}
