package com.tinuproject.tinu.domain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NotExistCodeException() : BaseException(ErrorCode.NOT_EXIST_CODE) {
}