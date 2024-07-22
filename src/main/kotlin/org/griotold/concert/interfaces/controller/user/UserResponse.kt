package org.griotold.concert.interfaces.controller.user

import org.griotold.concert.domain.user.User

class UserResponse {

    class Point(
        val userId: Long,
        val userName: String,
        val point: Int,
    ) {
        companion object {

            fun toResponse(user: User): Point {
                return Point(
                    userId = user.userId,
                    userName = user.name,
                    point = user.point.amount
                )
            }
        }
    }
}
