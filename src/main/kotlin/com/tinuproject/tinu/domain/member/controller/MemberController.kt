package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.security.jwt.JwtUtil
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api/user")
class MemberController(
    val jwtUtil: JwtUtil,
    val memberService: MemberService,
) {





}