package com.tinuproject.tinu.domain.post.dto.response

import java.time.LocalDateTime

data class PostListBodyResponse(
        val id: Long?,
        val createdAt: LocalDateTime?,
        val title: String,
        val price: Int,
        val thumbnail: String?,
        val isLike: Boolean,
        val likeCount: Long,
        val isSell: Boolean
)
