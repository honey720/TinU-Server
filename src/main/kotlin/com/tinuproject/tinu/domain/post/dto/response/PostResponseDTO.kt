package com.tinuproject.tinu.domain.post.dto.response

import java.time.LocalDateTime

class PostResponseDTO (
        postId: Long,
        createdAt: LocalDateTime?,
        title: String,
        price: Int,
        thumbnail: String?,
        isLike: Boolean,
        isSell: Boolean
)