package com.tinuproject.tinu.domain.common.exception.base

import com.tinuproject.tinu.domain.common.response.ResponseDTO

interface BaseCode {
    fun <T> getResponse(): ResponseDTO<T>?
}