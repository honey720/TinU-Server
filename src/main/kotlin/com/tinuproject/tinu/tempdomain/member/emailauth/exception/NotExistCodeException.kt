package com.tinuproject.tinu.tempdomain.member.emailauth.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotExistCodeException() : BaseException(ErrorCode.NOT_EXIST_CODE) {
}