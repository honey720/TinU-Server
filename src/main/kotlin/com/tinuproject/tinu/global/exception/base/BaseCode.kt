package com.tinuproject.tinu.global.exception.base

import com.tinuproject.tinu.global.response.dto.ResponseDTO

interface BaseCode {
    fun <T> getResponse(): ResponseDTO<T>?
}