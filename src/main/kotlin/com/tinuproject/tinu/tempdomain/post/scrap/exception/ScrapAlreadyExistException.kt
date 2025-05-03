package com.tinuproject.tinu.tempdomain.post.scrap.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class ScrapAlreadyExistException: BaseException(ErrorCode.SCRAP_ALREADY_EXIST) {
}