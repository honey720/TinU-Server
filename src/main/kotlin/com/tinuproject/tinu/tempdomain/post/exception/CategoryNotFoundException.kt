package com.tinuproject.tinu.tempdomain.post.exception

import com.tinuproject.tinu.tempdomain.common.exception.base.BaseException
import com.tinuproject.tinu.tempdomain.common.exception.base.ErrorCode

class CategoryNotFoundException: BaseException(ErrorCode.CATEGORY_NOT_FOUND) {
}