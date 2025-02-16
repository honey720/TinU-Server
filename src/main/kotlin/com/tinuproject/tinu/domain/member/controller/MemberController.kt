package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.security.jwt.JwtUtil
import com.tinuproject.tinu.web.ResponseEntityGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


@Controller
@RequestMapping("/api/user")
class MemberController(
    val jwtUtil: JwtUtil,
    val memberService: MemberService,
) {
    var log : Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/find/member")
    fun findMember(@AuthenticationPrincipal userId :String) : ResponseEntity<ResponseDTO> {
        var result = memberService.findMemberByUserId(UUID.fromString(userId))
        log.info(userId)

        return ResponseEntityGenerator.onSuccess(result)
    }



}