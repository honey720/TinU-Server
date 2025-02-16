package com.tinuproject.tinu.domain.exception.member

import com.tinuproject.tinu.domain.exception.base.BaseErrorCode
import com.tinuproject.tinu.domain.exception.base.BaseException
import com.tinuproject.tinu.domain.exception.base.ErrorCode

class NotMatchCodeException():BaseException(ErrorCode.NOT_MATCH_CODE) {
}