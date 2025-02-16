package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.exception.member.NotExistCodeException
import com.tinuproject.tinu.domain.exception.member.NotMatchCodeException
import com.tinuproject.tinu.web.email.dto.client_controller.EmailAuthRequestDTO
import com.tinuproject.tinu.web.email.dto.client_controller.EmailCodeCheckRequestDTO
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
    override fun sendMail(userId : String, emailAuthRequestDTO: EmailAuthRequestDTO) {

        log.info(emailAuthRequestDTO.email)
        val code = mailSender.sendMail(emailAuthRequestDTO.email)

        val existEMail = eMailRepository.findByUserId(userId = userId)

        if(existEMail!=null){
            log.info((emailAuthRequestDTO.email + " 계정의 기존 인증코드를 삭제합니다"))
            eMailRepository.delete(existEMail)
        }

        eMailRepository.save(EMailAuth(userId = userId, code = code))
    }

    override fun checkCode(userId : String, emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) : Boolean {
        val eMailAuth = eMailRepository.findByUserId(userId)

        eMailAuth ?: throw NotExistCodeException()

        return if(eMailAuth.code == emailCodeCheckRequestDTO.code){
            log.info("검증에 성공하였습니다.  계정의 기존 인증코드를 삭제합니다")
            eMailRepository.deleteById(eMailAuth.id!!)
            true
        } else{
            throw NotMatchCodeException()
        }
    }
}