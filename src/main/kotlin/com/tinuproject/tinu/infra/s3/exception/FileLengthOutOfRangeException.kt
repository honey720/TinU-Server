package com.tinuproject.tinu.infra.s3.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class FileLengthOutOfRangeException(): BaseException(ErrorCode.FILE_LENGTH_OUT_OF_RANGE) {
}