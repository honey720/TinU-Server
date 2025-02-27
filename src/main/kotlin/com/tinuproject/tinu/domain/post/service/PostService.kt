package com.tinuproject.tinu.domain.post.service

import com.tinuproject.tinu.domain.post.dto.response.PostsListResponseDTO
import java.util.UUID

interface PostService {
    fun getPostList(
            userId: UUID,
            cursorId: String?,
            keyword: String?,
            category: List<Long>?,
            minPrice: Int?,
            maxPrice: Int?,
            onlySell: Boolean,
            orderBy: String
    ): PostsListResponseDTO
}