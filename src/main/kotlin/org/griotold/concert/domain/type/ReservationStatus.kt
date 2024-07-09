package org.griotold.concert.domain.type
/**
 * 예약 상태
 * - TEMPORARY : 임시 배정
 * - SUCCESS : 예약 성공
 * - FAIL : 예약 실패
 * */
enum class ReservationStatus {
    TEMPORARY, SUCCESS, FAIL
}