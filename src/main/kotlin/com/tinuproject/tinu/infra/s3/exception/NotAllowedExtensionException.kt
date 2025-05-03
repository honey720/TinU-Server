package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class NotAllowedExtensionException(): BaseException(ErrorCode.NOT_ALLOWED_EXTENSION) {
}