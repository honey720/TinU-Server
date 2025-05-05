package com.tinuproject.tinu.domain.post.scrap.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class ScrapAlreadyExistException: BaseException(ErrorCode.SCRAP_ALREADY_EXIST) {
}