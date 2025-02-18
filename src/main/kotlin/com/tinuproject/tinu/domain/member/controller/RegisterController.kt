package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.exception.member.ExistEmailException
import com.tinuproject.tinu.domain.exception.university.NotExistDomainException
import com.tinuproject.tinu.domain.member.dto.controller.NickNameReservRequestDTO
import com.tinuproject.tinu.domain.member.dto.controller.RegisterRequestDTO
import com.tinuproject.tinu.web.email.dto.client_controller.EmailAuthRequestDTO
import com.tinuproject.tinu.web.email.dto.client_controller.EmailCodeCheckRequestDTO
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.domain.member.service.RegisterService
import com.tinuproject.tinu.domain.university.service.UniversityService
import com.tinuproject.tinu.web.ResponseEntityGenerator
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
@RequestMapping("/api/register")
class RegisterController(
    val memberService: MemberService,
    val universityService: UniversityService,
    val registerService: RegisterService
) {
    var log : Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/email-check")
    fun emailCheck(@AuthenticationPrincipal userId : String,@RequestParam(name = "email") email : String) : ResponseEntity<ResponseDTO> {
        checkEmailValidation(userId,email)

        return ResponseEntityGenerator.onSuccess()
    }

    //인증번호 요청
    @PostMapping("/email-auth")
    fun emailCodeRequest(@AuthenticationPrincipal userId : String, @RequestBody emailAuthRequestDTO: EmailAuthRequestDTO) : ResponseEntity<ResponseDTO>{
        checkEmailValidation(userId,emailAuthRequestDTO.email)

        registerService.sendMail(userId,emailAuthRequestDTO)

        return ResponseEntityGenerator.onSuccess()
    }

    @PostMapping(("/code-check"))
    fun emailCodeCheckRequest(@AuthenticationPrincipal userId : String, @RequestBody emailCodeCheckRequestDTO: EmailCodeCheckRequestDTO) : ResponseEntity<ResponseDTO>{
        registerService.checkCode(userId, emailCodeCheckRequestDTO)
        return ResponseEntityGenerator.onSuccess()
    }

    @GetMapping("/nick-check")
    fun nickNameCheckRequest(@AuthenticationPrincipal userId : String,@RequestParam(name = "name") nickName :String) : ResponseEntity<ResponseDTO>{
        memberService.existMemberByNickName(userId,nickName)
        return ResponseEntityGenerator.onSuccess()
    }

    @PutMapping("/nick-reserv")
    fun nickNameReservRequest(@AuthenticationPrincipal userId : String, @RequestBody nickNameReservRequestDTO: NickNameReservRequestDTO) : ResponseEntity<ResponseDTO>{
        memberService.existMemberByNickName(userId, nickNameReservRequestDTO.name)
        memberService.insertNickName(UUID.fromString(userId), nickNameReservRequestDTO.name)
        return ResponseEntityGenerator.onSuccess()
    }

    @PutMapping("/info-verify")
    fun registerRequest(@AuthenticationPrincipal userId:String, @RequestBody registerRequestDTO : RegisterRequestDTO) : ResponseEntity<ResponseDTO>{
        memberService.registerMember(userId = UUID.fromString(userId), registerRequestDTO = registerRequestDTO)

        return ResponseEntityGenerator.onSuccess()
    }

    fun checkEmailValidation(userId : String, email : String) {
        val domain = email.split("@")[1]
        memberService.existMemberByEmail(UUID.fromString(userId),email)

        universityService.existDomain(domain)
    }
}