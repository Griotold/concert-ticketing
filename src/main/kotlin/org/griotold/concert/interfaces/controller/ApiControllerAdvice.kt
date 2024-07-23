package org.griotold.concert.interfaces.controller

import org.griotold.concert.domain.common.CommonException
import org.griotold.concert.domain.common.CustomException
import org.griotold.concert.domain.performance.PerformanceException
import org.griotold.concert.domain.queue.QueueException
import org.griotold.concert.domain.reservation.ReservationException
import org.griotold.concert.domain.user.UserException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiControllerAdvice {
    val log: Logger = LoggerFactory.getLogger(ApiControllerAdvice::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        CommonException::class,
        UserException::class,
        PerformanceException::class,
        ReservationException::class,
        QueueException::class,
    )
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
