package org.griotold.concert.domain.queue

import org.griotold.concert.domain.common.CustomException
import org.griotold.concert.domain.common.ResponseCode

class QueueException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(queueResponseCode: QueueResponseCode) : super(queueResponseCode.msg) {
        this.responseCode = queueResponseCode
        this.msg = queueResponseCode.msg
    }

    constructor(queueResponseCode: QueueResponseCode, message: String) : super(message) {
        this.responseCode = queueResponseCode
        this.msg = message
    }
}