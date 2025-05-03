package com.tinuproject.tinu.tempdomain.member.customfilter.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotExistCustomFilter(): BaseException(ErrorCode.FILTER_NOT_EXIST) {
}