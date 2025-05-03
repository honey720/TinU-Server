package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NoSuchKeyException(): BaseException(ErrorCode.NO_SUCH_KEY) {
}