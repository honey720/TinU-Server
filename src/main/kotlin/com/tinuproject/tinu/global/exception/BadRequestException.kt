package com.tinuproject.tinu.global.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class BadRequestException() : BaseException(ErrorCode.BAD_REQUEST) {
}