package com.tinuproject.tinu.global.exception.base


import com.tinuproject.tinu.global.response.dto.ErrorResponse
import com.tinuproject.tinu.global.response.dto.ResponseDTO

interface BaseErrorCode {
    fun getResponse(): ResponseDTO<ErrorResponse>?
}