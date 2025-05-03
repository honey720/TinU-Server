package com.tinuproject.tinu.domain.member.emailauth.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotExistCodeException() : BaseException(ErrorCode.NOT_EXIST_CODE) {
}