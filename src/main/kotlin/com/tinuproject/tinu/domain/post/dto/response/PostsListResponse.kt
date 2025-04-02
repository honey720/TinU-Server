package com.tinuproject.tinu.domain.post.dto.response



data class PostsListResponse (
        val posts: List<PostResponse>,
        val size: Int,
        val nextCursorId: String
)