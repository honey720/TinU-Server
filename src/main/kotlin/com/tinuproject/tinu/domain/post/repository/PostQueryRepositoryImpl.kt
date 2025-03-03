package com.tinuproject.tinu.domain.post.repository

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.StringExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.tinuproject.tinu.domain.entity.Post
import com.tinuproject.tinu.domain.entity.QPost
import com.tinuproject.tinu.domain.entity.University
import org.springframework.stereotype.Repository

@Repository
class PostQueryRepositoryImpl(
        private val queryFactory: JPAQueryFactory
): PostQueryRepository {
    val post: QPost = QPost.post

    override fun findPosts(
            university: University,
            cursorId: String?,
            size: Long,
            keyword: String?,
            category: List<Long>?,
            minPrice: Int?,
            maxPrice: Int?,
            onlySell: Boolean,
            orderBy: String
    ): List<Post> {
        return queryFactory
                .selectFrom(post)
                .where(
                        post.university.eq(university),
                        customCursor(orderBy, cursorId),
                        containsTitle(keyword),
                        containsBody(keyword),
                        inCategory(category),
                        betweenPrice(maxPrice, minPrice),
                        eqOnlySell(onlySell)
                )
                .limit(size + 1)
                .orderBy(orderBy(orderBy))
                .fetch()
    }

    override fun customCursor(orderBy: String, cursorId: String?): BooleanExpression? {
        if (cursorId.isNullOrBlank()) {
            return null
        }
        else if (orderBy == "popular") {
            return StringExpressions.lpad(post.scrapCount.stringValue(), 10, '0')
                    .concat(StringExpressions.lpad(post.id.stringValue(), 10, '0'))
                    .lt(cursorId)
        }
        return post.id.stringValue().lt(cursorId)
    }

    override fun containsTitle(keyword: String?): BooleanExpression? {
        return when {
            keyword.isNullOrBlank() -> post.title.contains(keyword)
            else -> null
        }
    }

    override fun containsBody(keyword: String?): BooleanExpression? {
        return when {
            keyword.isNullOrBlank() -> post.body.contains(keyword)
            else -> null
        }
    }

    override fun inCategory(category: List<Long>?): BooleanExpression? {
        return when {
            category.isNullOrEmpty() -> null
            else -> post.category.id.`in`(category)
        }
    }

    override fun betweenPrice(maxPrice: Int?, minPrice: Int?): BooleanExpression? {
        return when {
            maxPrice != null && minPrice != null && maxPrice >= minPrice -> post.price.between(minPrice, maxPrice)
            minPrice != null -> post.price.goe(minPrice)
            maxPrice != null -> post.price.loe(maxPrice)
            else -> null
        }
    }

    override fun eqOnlySell(onlySell: Boolean): BooleanExpression? {
        return when (onlySell) {
            true -> post.isSell.eq(true)
            false -> null
        }
    }

    override fun orderBy(orderBy: String): OrderSpecifier<*>? {
        return when (orderBy) {
            "popular" -> post.scrapCount.desc()
            else -> post.createdAt.desc()
        }
    }
}