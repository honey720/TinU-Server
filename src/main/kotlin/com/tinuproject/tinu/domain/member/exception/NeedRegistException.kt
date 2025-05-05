package com.tinuproject.tinu.domain.member.exception

import com.tinuproject.tinu.global.exception.base.BaseException
import com.tinuproject.tinu.global.exception.base.ErrorCode

class NeedRegistException () : BaseException(ErrorCode.MEMBER_NEED_REGIST){
}