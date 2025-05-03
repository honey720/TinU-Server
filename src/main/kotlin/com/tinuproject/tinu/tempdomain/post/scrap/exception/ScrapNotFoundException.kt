package com.tinuproject.tinu.tempdomain.post.scrap.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class ScrapNotFoundException: BaseException(ErrorCode.SCRAP_NOT_FOUND) {
}