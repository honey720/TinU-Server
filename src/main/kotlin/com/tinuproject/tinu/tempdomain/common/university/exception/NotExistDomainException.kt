package com.tinuproject.tinu.tempdomain.common.university.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotExistDomainException() : BaseException(ErrorCode.UNIVERSITY_NOT_EXIST_DOMAIN) {
}