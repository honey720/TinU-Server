package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class NotAllowedExtensionException(): BaseException(ErrorCode.NOT_ALLOWED_EXTENSION) {
}