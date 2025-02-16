package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.exception.member.ExistEmailException
import com.tinuproject.tinu.domain.exception.university.NotExistDomainException
import com.tinuproject.tinu.domain.member.dto.EmailAuthRequestDTO
import com.tinuproject.tinu.domain.member.dto.EmailCodeCheckRequestDTO
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.domain.member.service.RegisterService
import com.tinuproject.tinu.domain.university.service.UniversityService
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.ResponseEntityGenerator
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
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

        return ResponseEntityGenerator.onSuccess()
    }

    //인증번호 요청
    @PostMapping("/email-auth")
    fun emailCodeRequest(httpServeletRequest: HttpServletRequest, @RequestBody emailAuthRequestDTO: EmailAuthRequestDTO) : ResponseEntity<ResponseDTO>{
        checkEmailVaildation(emailAuthRequestDTO.email)

        registerService.sendMail(httpServeletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7),emailAuthRequestDTO)

        return ResponseEntityGenerator.onSuccess()
    }

    @PostMapping(("/code-check"))
    fun emailCodeCheckRequest(httpServeletRequest: HttpServletRequest, @RequestParam emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) : ResponseEntity<ResponseDTO>{


//        registerService.checkCode()

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