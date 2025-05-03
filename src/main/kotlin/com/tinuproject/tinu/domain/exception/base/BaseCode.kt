package com.tinuproject.tinu.domain.exception.base

import com.tinuproject.tinu.tempdomain.common.response.ResponseDTO

interface BaseCode {
    fun <T> getResponse(): ResponseDTO<T>?
}