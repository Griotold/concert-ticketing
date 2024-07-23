package org.griotold.concert.interfaces.controller.common

import jakarta.servlet.http.HttpServletRequest
import org.griotold.concert.domain.common.CommonException
import org.griotold.concert.domain.common.CommonResponseCode
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class QueueTokenArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(QueueToken::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {

        val httpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)

        if (httpServletRequest != null) {
            val token = httpServletRequest.getHeader("wq-token")

            if (token != null) {
                return token
            }
        }

        throw CommonException(CommonResponseCode.ACCESS_DENIED)
    }
}