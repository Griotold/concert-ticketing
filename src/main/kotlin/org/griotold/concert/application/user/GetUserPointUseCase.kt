package org.griotold.concert.application.user

import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class GetUserPointUseCase (
    private val userService: UserService
) {
    operator fun invoke(command: UserCommand.GetPoint): User {
        return userService.getUser(command.userId)
    }
}