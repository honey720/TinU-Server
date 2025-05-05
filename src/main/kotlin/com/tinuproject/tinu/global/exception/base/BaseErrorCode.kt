package com.tinuproject.tinu.global.exception.base


import com.tinuproject.tinu.global.response.ErrorResponse
import com.tinuproject.tinu.global.response.ResponseDTO

interface BaseErrorCode {
    fun getResponse(): ResponseDTO<ErrorResponse>?
}