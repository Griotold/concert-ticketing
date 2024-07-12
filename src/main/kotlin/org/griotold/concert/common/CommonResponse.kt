package org.griotold.concert.common

import org.griotold.concert.common.ex.CommonResponseCode
import org.griotold.concert.common.ex.ResponseCode

data class CommonResponse<T>(
    var code: Int,
    val message: String,
    val body: T? = null,
) {

    companion object {

        fun <T> ok(): CommonResponse<T> {
            return process(CommonResponseCode.SUCCESS.code, CommonResponseCode.SUCCESS.msg, null)
        }

        fun <T> ok(body: T): CommonResponse<T> {
            return process(0, "success", body)
        }

        fun <T> error(responseCode: ResponseCode): CommonResponse<T> {
            return process(responseCode.code, responseCode.msg, null)
        }

        fun <T> error(responseCode: ResponseCode, message: String): CommonResponse<T> {
            return process(responseCode.code, message, null)
        }

        private fun <T> process(code: Int, message: String, body: T?): CommonResponse<T> {
            return CommonResponse(code, message, body)
        }
    }
}