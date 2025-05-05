package com.tinuproject.tinu.domain.post.scrap.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class ScrapNotFoundException: BaseException(ErrorCode.SCRAP_NOT_FOUND) {
}