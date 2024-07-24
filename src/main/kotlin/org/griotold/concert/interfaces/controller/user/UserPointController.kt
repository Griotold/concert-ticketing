package org.griotold.concert.interfaces.controller.user

import org.griotold.concert.application.user.ChargeUserPointUseCase
import org.griotold.concert.application.user.GetUserPointUseCase
import org.griotold.concert.application.user.command.UserCommand
import org.griotold.concert.interfaces.controller.CommonResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserPointController(
    private val getUserPointUseCase: GetUserPointUseCase,
    private val chargeUserPointUseCase: ChargeUserPointUseCase,
) {
    @GetMapping("/{user-id}/point")
    fun getUserPoint(@PathVariable("user-id") userId: Long): CommonResponse<UserResponse.Point> {
        val command = UserCommand.GetPoint(userId)
        val result = getUserPointUseCase(command)
        return CommonResponse.ok(UserResponse.Point.toResponse(result))
    }

    @PutMapping("/{user-id}/point")
    fun chargeUserPoint(
        @PathVariable("user-id") userId: Long,
        @RequestBody request: UserRequest.ChargePoint,
    ): CommonResponse<Unit> {
        val command = UserCommand.ChargePoint(userId, request.amount)
        chargeUserPointUseCase(command)
        return CommonResponse.ok()
    }
}
