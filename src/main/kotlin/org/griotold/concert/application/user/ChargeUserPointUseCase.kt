package org.griotold.concert.application.user

import org.griotold.concert.application.UseCase
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.domain.user.User
import org.griotold.concert.domain.user.UserService
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class ChargeUserPointUseCase(
    private val userService: UserService,
) {

    /**
     * 코틀린에서 invoke 메서드를 정의하면, 클래스의 인스턴스를 함수처럼 호출할 수 있다.
     * chargeUserPointUseCase(command) 이런식으로
     * */
    operator fun invoke(command: UserCommand.ChargePoint): User {
        val user = userService.getUser(command.userId)
        return userService.chargePoint(user, command.amount)
    }
}
