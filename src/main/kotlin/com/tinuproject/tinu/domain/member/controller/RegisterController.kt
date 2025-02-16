package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.exception.member.ExistEmailException
import com.tinuproject.tinu.domain.exception.university.NotExistDomainException
import com.tinuproject.tinu.web.email.dto.client_controller.EmailAuthRequestDTO
import com.tinuproject.tinu.web.email.dto.client_controller.EmailCodeCheckRequestDTO
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.domain.member.service.RegisterService
import com.tinuproject.tinu.domain.university.service.UniversityService
import com.tinuproject.tinu.web.ResponseEntityGenerator
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/api/register")
class RegisterController(
    val memberService: MemberService,
    val universityService: UniversityService,
    val registerService: RegisterService
) {

    @GetMapping("/email-check")
    fun emailCheck(@AuthenticationPrincipal userId : String,@RequestParam(name = "email") email : String) : ResponseEntity<ResponseDTO> {
        checkEmailVaildation(email)

        return ResponseEntityGenerator.onSuccess()
    }

    //인증번호 요청
    @PostMapping("/email-auth")
    fun emailCodeRequest(@AuthenticationPrincipal userId : String, @RequestBody emailAuthRequestDTO: EmailAuthRequestDTO) : ResponseEntity<ResponseDTO>{
        checkEmailVaildation(emailAuthRequestDTO.email)

        registerService.sendMail(userId,emailAuthRequestDTO)

        return ResponseEntityGenerator.onSuccess()
    }

    @PostMapping(("/code-check"))
    fun emailCodeCheckRequest(@AuthenticationPrincipal userId : String, @RequestBody emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) : ResponseEntity<ResponseDTO>{
        registerService.checkCode(userId, emailCodeCheckRequestDTO)
        return ResponseEntityGenerator.onSuccess()
    }

    @GetMapping("/test/authentication")
    fun authentication(@AuthenticationPrincipal userId : String) : ResponseEntity<ResponseDTO>{


        return ResponseEntityGenerator.onSuccess(userId)
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