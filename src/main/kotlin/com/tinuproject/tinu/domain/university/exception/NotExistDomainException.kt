package com.tinuproject.tinu.domain.university.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NotExistDomainException() : BaseException(ErrorCode.UNIVERSITY_NOT_EXIST_DOMAIN) {
}