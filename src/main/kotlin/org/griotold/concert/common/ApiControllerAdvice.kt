package org.griotold.concert.common

import org.griotold.concert.common.ex.BadRequestException
import org.griotold.concert.common.ex.CustomException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiControllerAdvice {
    val log: Logger = LoggerFactory.getLogger(ApiControllerAdvice::class.java)

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: CustomException): CommonResponse<Any> {
        log.warn("CustomException : {}", ex.msg)
        return CommonResponse.error(ex.responseCode, ex.msg)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): String {
        log.error("Exception : {}", ex.message, ex)
        return ex.localizedMessage
    }
}