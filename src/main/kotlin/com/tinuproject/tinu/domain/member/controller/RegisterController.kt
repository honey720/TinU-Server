package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.exception.member.ExistEmailException
import com.tinuproject.tinu.domain.exception.university.NotExistDomainException
import com.tinuproject.tinu.domain.member.dto.EmailAuthRequestDTO
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.domain.member.service.RegisterService
import com.tinuproject.tinu.domain.university.service.UniversityService
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.ResponseEntityGenerator
import com.tinuproject.tinu.web.email.util.MailSender
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/api/register")
class RegisterController(
    val memberService: MemberService,
    val universityService: UniversityService,
    val registerService: RegisterService,
    val jwtUtil: JwtUtil

) {

    @GetMapping("/email-check")
    fun emailCheck(httpServeletRequest:HttpServletRequest,@RequestParam(name = "email") email : String) : ResponseEntity<ResponseDTO> {
        checkEmailVaildation(email)

        return ResponseEntityGenerator.onSuccess(null)
    }

    //인증번호 요청
    @PostMapping("/email-auth")
    fun emailCodeRequest(httpServeletRequest: HttpServletRequest, @RequestBody emailAuthRequestDTO: EmailAuthRequestDTO){
        checkEmailVaildation(emailAuthRequestDTO.email)

        registerService.sendMail(jwtUtil.getTokenFromHeader(httpServeletRequest),emailAuthRequestDTO)


    }

    fun checkEmailVaildation(email : String) {
        val domain = email.split("@")[1]
        if(memberService.existMemberByEmail(email)){
            throw ExistEmailException()
        }else if(!universityService.existDomain(domain)){
            throw NotExistDomainException()
        }
    }
}