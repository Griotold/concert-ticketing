package org.griotold.concert.domain.user

import org.griotold.concert.domain.common.BadRequestException
import org.griotold.concert.domain.common.CommonResponseCode.BAD_REQUEST
import org.griotold.concert.domain.user.UserResponseCode.*

data class UserPoint(
    val amount: Int,
) {
    init {
        if (amount < 0) throw BadRequestException(BAD_REQUEST, "포인트는 음수로 초기화 할 수 없습니다.")
    }

    fun charge(chargeAmount: Int): UserPoint {
        if(chargeAmount < 0) throw BadRequestException(BAD_REQUEST, "충전 포인트는 0 이상 이어야 합니다.")
        val after = amount + chargeAmount
        return UserPoint(after)
    }

    fun use(useAmount: Int): UserPoint {
        if(useAmount < 0) throw BadRequestException(BAD_REQUEST, "사용 포인트는 0 이상 이어야 합니다.")
        val after = amount - useAmount
        if (after < 0) throw UserException(NOT_ENOUGH_BALANCE)
        return UserPoint(after)
    }
}