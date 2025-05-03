package com.tinuproject.tinu.domain.member.customfilter.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotExistCustomFilter(): BaseException(ErrorCode.FILTER_NOT_EXIST) {
}