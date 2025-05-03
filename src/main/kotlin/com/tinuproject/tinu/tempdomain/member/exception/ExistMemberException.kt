package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class ExistMemberException() : BaseException(ErrorCode.MEMBER_EXIST) {
}