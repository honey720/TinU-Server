package com.tinuproject.tinu.domain.post.service

import com.tinuproject.tinu.domain.entity.Post
import org.springframework.data.domain.Slice
import java.util.UUID

interface PostService {
    fun getPostList(
            userId: UUID,
            cursorId: Long?,
            keyword: String?,
            category: List<Long>?,
            minPrice: Int?,
            maxPrice: Int?,
            onlySell: Boolean,
            orderBy: String
    ): Slice<Post>
}