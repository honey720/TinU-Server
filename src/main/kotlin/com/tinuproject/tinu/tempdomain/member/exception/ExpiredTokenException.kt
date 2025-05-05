package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class ExpiredTokenException(): BaseException(errorCode = ErrorCode.TOKEN_EXPIRED) {
}