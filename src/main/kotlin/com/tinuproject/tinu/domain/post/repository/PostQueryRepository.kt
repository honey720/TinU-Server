package com.tinuproject.tinu.domain.post.repository

import com.querydsl.core.types.dsl.BooleanExpression
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

    fun containsTitle(
            keyword: String?
    ): BooleanExpression?

    fun containsBody(
            keyword: String?
    ): BooleanExpression?

    fun inCategory(
            category: List<Long>?
    ): BooleanExpression?

    fun betweenPrice(
            maxPrice: Int?,
            minPrice: Int?
    ): BooleanExpression?

    fun eqOnlySell(
            onlySell: Boolean
    ): BooleanExpression?

    fun orderBy(
            orderBy: String
    ): BooleanExpression?
}