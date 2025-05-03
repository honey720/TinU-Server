package com.tinuproject.tinu.tempdomain.common.exception.base

import com.tinuproject.tinu.tempdomain.common.response.ResponseDTO

interface BaseCode {
    fun <T> getResponse(): ResponseDTO<T>?
}