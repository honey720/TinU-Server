package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class ExistNameException() : BaseException(ErrorCode.MEMBER_EXIST_NICKNAME) {
}