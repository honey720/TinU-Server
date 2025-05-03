package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.emailauth.dto.request.EmailAuthRequestDTO
import com.tinuproject.tinu.domain.member.emailauth.dto.request.EmailCodeCheckRequestDTO
import java.util.UUID

interface RegisterService {
    fun sendMail(userId : UUID,emailAuthRequestDTO: EmailAuthRequestDTO)

    fun checkCode(userId : UUID, emailCodeCheckRequestDTO : EmailCodeCheckRequestDTO) : Boolean

    fun checkEmailValidation(userId : UUID, email : String) :Boolean


}