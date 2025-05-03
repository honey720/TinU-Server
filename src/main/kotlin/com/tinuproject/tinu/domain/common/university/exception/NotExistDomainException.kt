package com.tinuproject.tinu.domain.common.university.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotExistDomainException() : BaseException(ErrorCode.UNIVERSITY_NOT_EXIST_DOMAIN) {
}