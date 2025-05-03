package com.tinuproject.tinu.domain.post.scrap.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class ScrapNotFoundException: BaseException(ErrorCode.SCRAP_NOT_FOUND) {
}