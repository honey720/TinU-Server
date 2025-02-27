package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.member.dto.client_controller.request.UpdateUserInfoRequestDTO
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.ResponseEntityGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


@Controller
@RequestMapping("/api/user")
class MemberController(
    val memberService: MemberService,
) {
    var log : Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping()
    fun requestUserInfo(@AuthenticationPrincipal userId : UUID, @RequestParam(name = "userId") searchUserId : String? ) : ResponseEntity<ResponseDTO>{
        val findUserId = searchUserId?.let{UUID.fromString(it)}?:userId

        return ResponseEntityGenerator.onSuccess(memberService.findMemberByUserId(findUserId))
    }

    @PutMapping()
    fun requestUpdateUserInfo(@AuthenticationPrincipal userId: UUID, @RequestBody updateUserInfoRequestDTO: UpdateUserInfoRequestDTO) : ResponseEntity<ResponseDTO>{
        memberService.updateMember(userId, updateUserInfoRequestDTO)

        return ResponseEntityGenerator.onSuccess()
    }

    @GetMapping("/is-login")
    fun requestIsLogin(@AuthenticationPrincipal userId : UUID):ResponseEntity<ResponseDTO>{
        return ResponseEntityGenerator.onSuccess()
    }
}