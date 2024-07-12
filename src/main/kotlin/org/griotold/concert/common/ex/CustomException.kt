package org.griotold.concert.common.ex

interface CustomException {
    val responseCode: ResponseCode
    val msg: String
}