package com.tinuproject.tinu.domain.post.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.post.service.PostServiceImpl
import com.tinuproject.tinu.web.ResponseEntityGenerator
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/post")
class PostController(
        private val postServiceImpl: PostServiceImpl
) {
    @GetMapping
    fun getPosts(
            @AuthenticationPrincipal userId: UUID,
            @RequestParam(required = false) cursorId: Long?,
            @RequestParam(required = false) keyword: String?,
            @RequestParam(required = false) category: List<Long>?,
            @RequestParam(required = false) minPrice: Int?,
            @RequestParam(required = false) maxPrice: Int?,
            @RequestParam onlySell: Boolean,
            @RequestParam(defaultValue = "recent") orderBy: String
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(postServiceImpl.getPostList(
                userId,
                cursorId,
                keyword,
                category,
                minPrice,
                maxPrice,
                onlySell,
                orderBy
        ))
    }
}