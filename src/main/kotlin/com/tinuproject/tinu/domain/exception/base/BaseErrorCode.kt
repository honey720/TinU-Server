package com.tinuproject.tinu.domain.exception.base


import com.tinuproject.tinu.tempdomain.common.response.ErrorResponse
import com.tinuproject.tinu.tempdomain.common.response.ResponseDTO

interface BaseErrorCode {
    fun getResponse(): ResponseDTO<ErrorResponse>?
}