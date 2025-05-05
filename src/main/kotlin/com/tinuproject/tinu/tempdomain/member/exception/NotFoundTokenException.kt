package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NotFoundTokenException(
) : BaseException(errorCode = ErrorCode.TOKEN_MISSING) {

}