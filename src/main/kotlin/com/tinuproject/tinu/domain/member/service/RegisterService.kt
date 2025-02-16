package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.web.email.dto.client_controller.EmailAuthRequestDTO
import com.tinuproject.tinu.web.email.dto.client_controller.EmailCodeCheckRequestDTO

interface RegisterService {
    fun sendMail(userId : String,emailAuthRequestDTO: EmailAuthRequestDTO)

    fun checkCode(userId : String, emailCodeCheckRequestDTO : EmailCodeCheckRequestDTO) : Boolean
}