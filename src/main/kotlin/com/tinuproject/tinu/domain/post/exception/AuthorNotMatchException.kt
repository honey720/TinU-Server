package com.tinuproject.tinu.domain.post.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class AuthorNotMatchException: BaseException(ErrorCode.AUTHOR_NOT_MATCH) {
}