package com.tinuproject.tinu.tempdomain.common.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotFoundException() : BaseException(ErrorCode.NOT_FOUND) {
}