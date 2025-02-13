package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.dto.EmailAuthRequestDTO
import com.tinuproject.tinu.domain.member.dto.EmailCodeCheckRequestDTO
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.email.EMailRepository
import com.tinuproject.tinu.web.email.entity.EMailAuth
import com.tinuproject.tinu.web.email.util.MailSender
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(
    val jwtUtil: JwtUtil,
    val mailSender: MailSender,
    val eMailRepository: EMailRepository
):RegisterService {
    override fun sendMail(accessToken: String, emailAuthRequestDTO: EmailAuthRequestDTO) {
        val userId = jwtUtil.getUserIdFromToken(accessToken)

        val code = mailSender.sendMail(emailAuthRequestDTO.email)

        val existEMail = eMailRepository.findByUserId(userId = userId)

        if(existEMail!=null) eMailRepository.delete(existEMail)

        eMailRepository.save(EMailAuth(userId = userId, code = code))
    }

    override fun checkCode(accessToken: String, emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) {
        TODO("Not yet implemented")
    }
}