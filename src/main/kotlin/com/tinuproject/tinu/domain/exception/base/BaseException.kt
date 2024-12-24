package com.tinuproject.tinu.domain.exception.base

import java.lang.RuntimeException

open class BaseException(
    protected val errorCode : ErrorCode
): RuntimeException(), BaseErrorCode {

    override fun getResponse(): ResponseDTO {
        var map : MutableMap<String, Any> = mutableMapOf()
        map["error-message"] = errorCode.message
        if(errorCode.stateCode!=null) map["stateCode"] = errorCode.stateCode
        return ResponseDTO(isSuccess = false, httpStatusCode = errorCode.httpStatusCode, result = map)
    }
}