package com.tinuproject.tinu.domain.member.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class ExistMemberException() : BaseException(ErrorCode.MEMBER_EXIST) {
}