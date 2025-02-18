package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.entity.Member
import com.tinuproject.tinu.domain.exception.member.NotExistCodeException
import com.tinuproject.tinu.domain.exception.member.NotMatchCodeException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.socialmember.repository.SocialMemberRepository
import com.tinuproject.tinu.web.email.dto.client_controller.EmailAuthRequestDTO
import com.tinuproject.tinu.web.email.dto.client_controller.EmailCodeCheckRequestDTO
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.email.repository.EMailRepository
import com.tinuproject.tinu.web.email.entity.EMailAuth
import com.tinuproject.tinu.web.email.util.CustomMailSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RegisterServiceImpl(
    val jwtUtil: JwtUtil,
    val mailSender: CustomMailSender,
    val eMailRepository: EMailRepository,
    val socialMemberRepository : SocialMemberRepository,
    val memberRepository : MemberRepository
):RegisterService {
    var log : Logger = LoggerFactory.getLogger(this::class.java)


    @Transactional
    override fun sendMail(userId : String, emailAuthRequestDTO: EmailAuthRequestDTO) {

        log.info(emailAuthRequestDTO.email)
        log.info(userId)
        val code = mailSender.sendMail(emailAuthRequestDTO.email)

        val existEMail = eMailRepository.findByUserId(userId = userId)

        if(existEMail!=null){
            log.info((emailAuthRequestDTO.email + " 계정의 기존 인증코드를 삭제합니다"))
            eMailRepository.delete(existEMail)
        }

        eMailRepository.save(EMailAuth(userId = userId, eMail = emailAuthRequestDTO.email, code = code))
    }

    @Transactional
    override fun checkCode(userId : String, emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) : Boolean {
        val eMailAuth = eMailRepository.findByUserId(userId)
        log.info(userId)
        eMailAuth ?: throw NotExistCodeException()

        return if(eMailAuth.code == emailCodeCheckRequestDTO.code){

            
            val existMember = memberRepository.findMemberByUserId(UUID.fromString(userId))

            //member가 없는 경우 = 최초 이메일 인증
            if(existMember==null){
                val socialMember = socialMemberRepository.findByUserId(UUID.fromString(userId))
                val newMember = Member(
                    userId = UUID.fromString(userId),
                    nickname = null,
                    major = "중고거래학과",
                    grade = null,
                    gender = null,
                    profileImageURL = null,
                    introduction = null,
                    eMail = eMailAuth.eMail,
                    mark = null,
                    social = socialMember!!.provider,
                )

                memberRepository.save(newMember)
            //member가 있고, 이메일이 다른 경우 = 최초의 이메일 인증 메일과 지금이 다름
            }else if(eMailAuth.eMail != existMember.eMail){
                existMember.eMail = eMailAuth.eMail
                memberRepository.save(existMember)
            //member가 있고 , 이메일이 같은 경우, 별도의 로직 필요X로 생각
            //CHECK(맞을지 확인하고 삭제)
            }else{

            }

            log.info("검증에 성공하였습니다.  계정의 기존 인증코드를 삭제합니다")
            eMailRepository.deleteById(eMailAuth.id!!)
            true
        } else{
            throw NotMatchCodeException()
        }
    }
}