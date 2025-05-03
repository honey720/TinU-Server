package com.tinuproject.tinu.tempdomain.member.refreshtoken.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotFoundTokenException(
) : BaseException(errorCode = ErrorCode.TOKEN_MISSING) {

}