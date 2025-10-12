package com.tinuproject.tinu.domain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class ExistReviewException : BaseException(ErrorCode.REVIEW_ALREADY_EXIST) {
}