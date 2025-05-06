package com.tinuproject.tinu.global.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NotFoundException() : BaseException(ErrorCode.NOT_FOUND) {
}