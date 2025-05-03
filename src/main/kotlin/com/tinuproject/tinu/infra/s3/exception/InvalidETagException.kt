package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class InvalidETagException(): BaseException(ErrorCode.INVALID_ETAG) {
}