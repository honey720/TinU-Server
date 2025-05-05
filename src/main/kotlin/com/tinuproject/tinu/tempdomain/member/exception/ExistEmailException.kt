package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class ExistEmailException() : BaseException(ErrorCode.MEMBER_EXIST_EMAIL) {
}