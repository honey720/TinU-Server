package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NoSuchKeyException(): BaseException(ErrorCode.NO_SUCH_KEY) {
}