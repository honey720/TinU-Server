package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.dto.EmailAuthRequestDTO
import com.tinuproject.tinu.domain.member.dto.EmailCodeCheckRequestDTO

interface RegisterService {
    fun sendMail(accessToken : String,emailAuthRequestDTO: EmailAuthRequestDTO)

    fun checkCode(accessToken: String, emailCodeCheckRequestDTO : EmailCodeCheckRequestDTO)
}