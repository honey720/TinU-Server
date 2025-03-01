package com.tinuproject.tinu.domain.customfilter.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.customfilter.dto.client_controller.request.CreateCustomFilter
import com.tinuproject.tinu.domain.customfilter.service.CustomFilterService
import com.tinuproject.tinu.domain.member.service.MemberService
import com.tinuproject.tinu.web.ResponseEntityGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


@RequestMapping("/api/custom-filter")
class CustomFilterController(
    private val customFilterService: CustomFilterService
) {
    val log : Logger = LoggerFactory.getLogger(this::class.java)


    @GetMapping("")
    fun requestCustomFilter(@AuthenticationPrincipal userId : UUID) : ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(customFilterService.getCustomFilter(userId))
    }

    @PostMapping("")
    fun createCustomFilter(@AuthenticationPrincipal userId : UUID, createCustomFilter: CreateCustomFilter){
        customFilterService.createCustomFilter(userId = userId, createCustomFilter= createCustomFilter)
    }
}