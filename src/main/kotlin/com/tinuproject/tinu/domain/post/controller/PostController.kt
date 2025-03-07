package com.tinuproject.tinu.domain.post.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.post.dto.request.PostCreateRequest
import com.tinuproject.tinu.domain.post.dto.request.PostDeleteRequest
import com.tinuproject.tinu.domain.post.dto.request.PostUpdateRequest
import com.tinuproject.tinu.domain.post.service.PostService
import com.tinuproject.tinu.web.ResponseEntityGenerator
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/post")
class PostController(
        private val postService: PostService
) {
    @GetMapping
    fun getPosts(
            @AuthenticationPrincipal userId: UUID,
            @RequestParam(required = false) cursorId: String?,
            @RequestParam(required = false) keyword: String?,
            @RequestParam(required = false) category: List<Long>?,
            @RequestParam(required = false) minPrice: Int?,
            @RequestParam(required = false) maxPrice: Int?,
            @RequestParam onlySell: Boolean,
            @RequestParam(defaultValue = "recent") orderBy: String
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(postService.getPostList(
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

    @GetMapping("/{postId}")
    fun getPostDetail(
            @AuthenticationPrincipal userId: UUID,
            @PathVariable postId: Long
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(postService.getPostDetail(userId, postId))
    }

    @PostMapping
    fun createPost(
            @AuthenticationPrincipal userId: UUID,
            @RequestBody postCreateRequest: PostCreateRequest
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(postService.createPost(userId, postCreateRequest))
    }

    @PutMapping("/{postId}")
    fun updatePost(
            @AuthenticationPrincipal userId: UUID,
            @PathVariable postId: Long,
            @RequestBody postUpdateRequest: PostUpdateRequest
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(postService.updatePost(userId, postId, postUpdateRequest))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
            @AuthenticationPrincipal userId: UUID,
            @PathVariable postId: Long
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntityGenerator.onSuccess(postService.deletePost(userId, PostDeleteRequest(postId = postId)))
    }

}