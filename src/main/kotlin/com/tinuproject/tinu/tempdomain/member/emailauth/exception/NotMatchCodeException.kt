package com.tinuproject.tinu.tempdomain.member.emailauth.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotMatchCodeException(): BaseException(ErrorCode.NOT_MATCH_CODE) {
}