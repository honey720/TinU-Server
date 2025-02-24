package com.tinuproject.tinu.domain.post.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.tinuproject.tinu.domain.entity.Category
import com.tinuproject.tinu.domain.entity.Post
import com.tinuproject.tinu.domain.entity.University

interface PostQueryRepository {
    fun findPosts(
            university: University,
            cursorId: Long?,
            size: Long,
            keyword: String?,
            category: List<Long>?,
            minPrice: Int?,
            maxPrice: Int?,
            onlySell: Boolean,
            orderBy: String
    ): List<Post>

    fun ltPostId(
            cursorId: Long?
    ): BooleanExpression?

    fun containsPostTitle(
            keyword: String?
    ): BooleanExpression?

    fun containsPostBody(
            keyword: String?
    ): BooleanExpression?

    fun inPostCategory(
            category: List<Long>?
    ): BooleanExpression?

    fun betweenPostPrice(
            maxPrice: Int?,
            minPrice: Int?
    ): BooleanExpression?

    fun eqPostOnlySell(
            onlySell: Boolean
    ): BooleanExpression?

    fun postOrderBy(
            orderBy: String
    ): BooleanExpression?
}