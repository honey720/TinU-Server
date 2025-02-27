package com.tinuproject.tinu.domain.post.dto.response


class PostsListResponseDTO (
        val posts: List<PostResponseDTO>,
        val size: Int,
        val nextCursorId: String
)