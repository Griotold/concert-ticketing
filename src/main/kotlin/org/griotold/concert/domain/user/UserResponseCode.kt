package org.griotold.concert.domain.user

import org.griotold.concert.domain.common.ResponseCode

enum class UserResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    NOT_FOUND_USER(1404, "유저를 찾을 수 없습니다."),
    NOT_ENOUGH_BALANCE(1000, "잔액이 부족합니다."),
}
