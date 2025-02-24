package com.tinuproject.tinu.domain.post.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.tinuproject.tinu.domain.entity.Category
import com.tinuproject.tinu.domain.entity.Post
import com.tinuproject.tinu.domain.entity.QPost
import com.tinuproject.tinu.domain.entity.University
import org.springframework.stereotype.Repository
import kotlin.math.max

@Repository
class PostQueryRepositoryImpl(
        private val queryFactory: JPAQueryFactory
): PostQueryRepository {
    val post: QPost = QPost.post

    override fun findPosts(
            university: University,
            cursorId: Long?,
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
                        ltPostId(cursorId),
                        containsPostTitle(keyword),
                        containsPostBody(keyword),
                        inPostCategory(category),
                        betweenPostPrice(maxPrice, minPrice),
                        eqPostOnlySell(onlySell)
                )
                .limit(size + 1)
                .orderBy(post.createdAt.desc()) //TODO: 인기순 구현 예정
                .fetch()
    }

    override fun ltPostId(cursorId: Long?): BooleanExpression? {
        return when {
            cursorId != null -> post.id.lt(cursorId)
            else -> null
        }
    }

    override fun containsPostTitle(keyword: String?): BooleanExpression? {
        return when {
            keyword != null -> post.title.contains(keyword)
            else -> null
        }
    }

    override fun containsPostBody(keyword: String?): BooleanExpression? {
        return when {
            keyword != null -> post.body.contains(keyword)
            else -> null
        }
    }

    override fun inPostCategory(category: List<Long>?): BooleanExpression? {
        return when {
            category != null -> post.category.id.`in`(category)
            else -> null
        }
    }

    override fun betweenPostPrice(maxPrice: Int?, minPrice: Int?): BooleanExpression? {
        return when {
            maxPrice != null && minPrice != null && maxPrice >= minPrice -> post.price.between(minPrice, maxPrice)
            minPrice != null -> post.price.goe(minPrice)
            maxPrice != null -> post.price.loe(maxPrice)
            else -> null
        }
    }

    override fun eqPostOnlySell(onlySell: Boolean): BooleanExpression? {
        return when {
            onlySell -> post.isSell.eq(onlySell)
            else -> null
        }
    }

    override fun postOrderBy(orderBy: String): BooleanExpression? {
        TODO("Not yet implemented")
    }
}