package com.tinuproject.tinu.domain.common.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotFoundException() : BaseException(ErrorCode.NOT_FOUND) {
}