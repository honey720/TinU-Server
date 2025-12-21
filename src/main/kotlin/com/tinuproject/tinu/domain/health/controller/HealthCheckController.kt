package com.tinuproject.tinu.domain.health.controller

import com.tinuproject.tinu.global.response.ResponseEntityGenerator
import com.tinuproject.tinu.global.response.dto.NullResponse
import com.tinuproject.tinu.global.response.dto.ResponseDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
    @RequestMapping("/api/health")
class HealthCheckController {

    @GetMapping
    fun healthCheck() : ResponseEntity<ResponseDTO<NullResponse?>>{

        return ResponseEntityGenerator.onSuccess()
    }
}