package com.tinuproject.tinu.domain.member.emailauth.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NeedEmailAuthException() : BaseException(ErrorCode.NEED_EMAIL_AUTH) {
}