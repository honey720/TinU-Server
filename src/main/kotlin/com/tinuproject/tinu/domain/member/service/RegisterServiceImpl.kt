package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.dto.EmailAuthRequestDTO
import com.tinuproject.tinu.domain.member.dto.EmailCodeCheckRequestDTO
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.email.repository.EMailRepository
import com.tinuproject.tinu.web.email.entity.EMailAuth
import com.tinuproject.tinu.web.email.util.CustomMailSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(
    val jwtUtil: JwtUtil,
    val mailSender: CustomMailSender,
    val eMailRepository: EMailRepository
):RegisterService {
    var log : Logger = LoggerFactory.getLogger(this::class.java)
    override fun sendMail(accessToken: String, emailAuthRequestDTO: EmailAuthRequestDTO) {
        val userId = jwtUtil.getUserIdFromToken(accessToken)
        log.info(emailAuthRequestDTO.email)
        val code = mailSender.sendMail(emailAuthRequestDTO.email)

        val existEMail = eMailRepository.findByUserId(userId = userId)

        if(existEMail!=null) eMailRepository.delete(existEMail)

        eMailRepository.save(EMailAuth(userId = userId, code = code))
    }

    override fun checkCode(accessToken: String, emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) {
        TODO("Not yet implemented")
    }
}