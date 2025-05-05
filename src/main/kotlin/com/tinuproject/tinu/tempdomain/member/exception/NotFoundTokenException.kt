package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotFoundTokenException(
) : BaseException(errorCode = ErrorCode.TOKEN_MISSING) {

}