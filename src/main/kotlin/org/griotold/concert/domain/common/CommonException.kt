package org.griotold.concert.domain.common

class CommonException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(code: ResponseCode) : super(code.msg) {
        this.responseCode = code
        this.msg = code.msg
    }

    constructor(code: ResponseCode, message: String) : super(message) {
        this.responseCode = code
        this.msg = message
    }
}