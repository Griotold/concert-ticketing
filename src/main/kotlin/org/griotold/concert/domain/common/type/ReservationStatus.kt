package org.griotold.concert.domain.common.type
/**
 * 예약 상태
 * - RESERVED : 예약
 * - COMPLETE : 완료
 * - CANCEL : 취소
 * - EXPIRED : 만료
 * */
enum class ReservationStatus {
    RESERVED, COMPLETE, CANCEL, EXPIRED
}
