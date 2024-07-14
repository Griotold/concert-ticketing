package org.griotold.concert.application.user.command

import org.griotold.concert.domain.common.BadRequestException
import org.griotold.concert.domain.common.CommonResponseCode.BAD_REQUEST

class UserCommand {

    data class ChargePoint(
        val userId: Long,
        val amount: Int,
    ) {
        init {
            if (userId <= 0) throw BadRequestException(BAD_REQUEST, "userId는 양수 값 이어야 합니다.")
            if (amount <= 0) throw BadRequestException(BAD_REQUEST, "amount는 양수 값 이어야 합니다.")
        }
    }

    data class GetPoint(
        val userId: Long,
    ) {

        init {
            if (userId <= 0) throw BadRequestException(BAD_REQUEST, "userId는 양수 값 이어야 합니다.")
        }
    }

}