package com.tinuproject.tinu.tempdomain.member.exception

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.base.ErrorCode

class InvalidedTokenException(

): BaseException(errorCode= ErrorCode.TOKEN_INVALIDED) {}