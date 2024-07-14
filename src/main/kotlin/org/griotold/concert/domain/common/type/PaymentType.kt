package org.griotold.concert.domain.common.type
/**
 * 결제 유형
 * */
enum class PaymentType {
    BANK_TRANSFER, // 무통장 입금
    CREDIT_CARD, // 카드
    EASY_PAYMENT // 간편 결제
}