package com.tinuproject.tinu.domain.post.dto.response



data class PostsListResponse (
        val posts: List<PostListBodyResponse>,
        val size: Int,
        val nextCursorId: String
)