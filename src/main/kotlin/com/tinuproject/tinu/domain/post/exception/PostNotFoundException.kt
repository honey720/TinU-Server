package com.tinuproject.tinu.domain.post.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class PostNotFoundException: BaseException(ErrorCode.POST_NOT_FOUND) {
}