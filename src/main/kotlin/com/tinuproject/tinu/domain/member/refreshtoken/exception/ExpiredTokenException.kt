package com.tinuproject.tinu.domain.member.refreshtoken.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class ExpiredTokenException(): BaseException(errorCode = ErrorCode.TOKEN_EXPIRED) {
}