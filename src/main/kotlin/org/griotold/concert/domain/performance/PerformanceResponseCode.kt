package org.griotold.concert.domain.performance

import org.griotold.concert.domain.common.ResponseCode

enum class PerformanceResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    PERFORMANCE_NOT_FOUND(2404, "공연을 찾을 수 없습니다."),
    ALREADY_RESERVED(2000, "이미 예약된 좌석입니다."),
    PERFORMANCE_SEAT_NOT_FOUND(2001, "좌석을 찾을 수 없습니다.")
}
