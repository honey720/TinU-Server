package com.tinuproject.tinu.domain.member.emailauth.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotMatchCodeException(): BaseException(ErrorCode.NOT_MATCH_CODE) {
}