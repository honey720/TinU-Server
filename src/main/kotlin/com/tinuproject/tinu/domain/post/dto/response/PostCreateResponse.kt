package com.tinuproject.tinu.domain.post.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class PostCreateResponse(
        @Schema(description = "게시글 ID", defaultValue = "1")
        val postId: Long
)