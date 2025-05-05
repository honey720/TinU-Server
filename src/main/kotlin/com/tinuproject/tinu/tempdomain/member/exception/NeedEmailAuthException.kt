package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NeedEmailAuthException() : BaseException(ErrorCode.NEED_EMAIL_AUTH) {
}