package com.tinuproject.tinu.tempdomain.post.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class AuthorNotMatchException: BaseException(ErrorCode.AUTHOR_NOT_MATCH) {
}