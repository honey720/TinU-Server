package com.tinuproject.tinu.tempdomain.common.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class UnauthorizedAccessException() : BaseException(ErrorCode.UNAUTHORIZED_ACCESS) {
}