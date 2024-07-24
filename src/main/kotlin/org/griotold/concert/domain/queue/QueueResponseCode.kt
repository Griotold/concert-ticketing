package org.griotold.concert.domain.queue

import org.griotold.concert.domain.common.ResponseCode

enum class QueueResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    QUEUE_TOKEN_NOT_FOUND(4404, "토큰이 없습니다."),


}