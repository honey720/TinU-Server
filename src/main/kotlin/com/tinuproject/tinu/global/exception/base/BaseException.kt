package com.tinuproject.tinu.global.exception.base

import com.tinuproject.tinu.global.response.dto.ErrorResponse
import com.tinuproject.tinu.global.response.dto.ResponseDTO
import java.lang.RuntimeException

open class BaseException(
    protected val errorCode : ErrorCode
): RuntimeException(), BaseErrorCode {

    override fun getResponse(): ResponseDTO<ErrorResponse> {
        return ResponseDTO<ErrorResponse>(isSuccess = false, stateCode = errorCode.httpStatusCode, result = ErrorResponse(message=errorCode.message, statusCode = errorCode.stateCode))
    }
}