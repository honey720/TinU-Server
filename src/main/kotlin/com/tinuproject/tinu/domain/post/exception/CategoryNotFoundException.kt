package com.tinuproject.tinu.domain.post.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class CategoryNotFoundException: BaseException(ErrorCode.CATEGORY_NOT_FOUND) {
}