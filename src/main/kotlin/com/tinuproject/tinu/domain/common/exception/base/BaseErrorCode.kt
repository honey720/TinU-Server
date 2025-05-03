package com.tinuproject.tinu.domain.common.exception.base


import com.tinuproject.tinu.domain.common.response.ErrorResponse
import com.tinuproject.tinu.domain.common.response.ResponseDTO

interface BaseErrorCode {
    fun getResponse(): ResponseDTO<ErrorResponse>?
}