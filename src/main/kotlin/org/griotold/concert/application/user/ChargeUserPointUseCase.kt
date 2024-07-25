package org.griotold.concert.application.user

import org.griotold.concert.application.common.UseCase
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class ChargeUserPointUseCase(
    private val userService: UserService,
) {

    // 비관적락 걸기
    operator fun invoke(command: UserCommand.ChargePoint): User {
        val user = userService.getUserWithLock(command.userId)
        return userService.chargePoint(user, command.amount)
    }
}
