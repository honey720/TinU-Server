package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NotExistCustomFilter(): BaseException(ErrorCode.FILTER_NOT_EXIST) {
}