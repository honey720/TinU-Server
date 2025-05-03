package com.tinuproject.tinu.tempdomain.post.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class HashTagNotFoundException: BaseException(ErrorCode.HASHTAG_NOT_FOUND) {
}