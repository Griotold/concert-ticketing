package org.griotold.concert.domain.performance

import org.griotold.concert.domain.common.CustomException
import org.griotold.concert.domain.common.ResponseCode

class PerformanceException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(performanceResponseCode: PerformanceResponseCode) : super(performanceResponseCode.msg) {
        this.responseCode = performanceResponseCode
        this.msg = performanceResponseCode.msg
    }

    constructor(performanceResponseCode: PerformanceResponseCode, message: String) : super(message) {
        this.responseCode = performanceResponseCode
        this.msg = message
    }
}
