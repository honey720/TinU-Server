package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class UploadSizeOutOfRangeException() : BaseException(ErrorCode.UPLOAD_SIZE_OUT_OF_RANGE) {
}