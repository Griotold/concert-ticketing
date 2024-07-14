package org.griotold.concert.domain.common.type
/**
 * 좌석 상태
 * - AVAILABLE : 예약 가능
 * - TEMPORARY : 임시 배정
 * - SOLD_OUT : 예약 불가
 * */
enum class SeatStatus {
    AVAILABLE, TEMPORARY, SOLD_OUT
}