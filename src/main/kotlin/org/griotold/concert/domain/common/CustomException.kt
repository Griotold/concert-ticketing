package org.griotold.concert.domain.common

interface CustomException {
    val responseCode: ResponseCode
    val msg: String
}