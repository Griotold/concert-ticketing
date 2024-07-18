package org.griotold.concert.domain.common

import org.griotold.concert.domain.common.CommonResponseCode.BAD_REQUEST

class Pageable(
    val pageNo: Int,
    val pageSize: Int,
) {

    init {
        if (pageNo <= 0) throw BadRequestException(BAD_REQUEST, "pageNo는 1 이상 이어야 합니다.")
        if (pageSize <= 0) throw BadRequestException(BAD_REQUEST, "pageSize는 1 이상 이어야 합니다.")
    }
}