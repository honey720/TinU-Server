package com.tinuproject.tinu.tempdomain.member.emailauth.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NeedEmailAuthException() : BaseException(ErrorCode.NEED_EMAIL_AUTH) {
}