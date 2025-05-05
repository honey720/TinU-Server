package com.tinuproject.tinu.global.exception.base

import com.tinuproject.tinu.global.response.ResponseDTO

interface BaseCode {
    fun <T> getResponse(): ResponseDTO<T>?
}