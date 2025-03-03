package com.tinuproject.tinu.domain.post.service

import com.tinuproject.tinu.domain.exception.post.*
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.post.dto.response.PostDetailResponse
import com.tinuproject.tinu.domain.post.dto.response.PostsListResponse
import com.tinuproject.tinu.domain.post.repository.PostQueryRepository
import com.tinuproject.tinu.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import java.util.*

const val SIZE = 20
@Service
class PostServiceImpl(
        private val memberRepository: MemberRepository,
        private val postRepository: PostRepository,
        private val postQueryRepository: PostQueryRepository
): PostService {
    override fun getPostList(
            userId: UUID,
            cursorId: String?,
            keyword: String?,
            category: List<Long>?,
            minPrice: Int?,
            maxPrice: Int?,
            onlySell: Boolean,
            orderBy: String
    ): PostsListResponse {
        val member = memberRepository.findMemberByUserId(userId)
                ?: throw MemberNotFoundException()

        val university = member.university
                ?: throw UniversityNotFoundException()

        var rawPosts = postQueryRepository.findPosts(
                university,
                cursorId,
                SIZE.toLong(),
                keyword,
                category,
                minPrice,
                maxPrice,
                onlySell,
                orderBy
        )

        var nextCursorId = ""

        if (rawPosts.size > SIZE) {
            rawPosts = rawPosts.subList(0, SIZE)

            nextCursorId = if (orderBy == "popular") {
                String.format("%010d%020d", rawPosts.last().scrapCount, rawPosts.last().id)
            } else {
                rawPosts.last().id.toString()
            }
        }

        val posts = rawPosts.map { post ->
            PostsListResponse.PostResponse(
                    postId = post.id!!,
                    createdAt = post.createdAt!!,
                    title = post.title,
                    price = post.price,
                    thumbnail = post.thumbnail!!,
                    isLike = member.scrap.any { it.post == post },
                    isSell = post.isSell
            )
        }

        return PostsListResponse(
                posts = posts,
                size = posts.size,
                nextCursorId = nextCursorId
        )
    }

    override fun getPostDetail(userId: UUID, postId: Long): PostDetailResponse {
        val member = memberRepository.findMemberByUserId(userId)
                ?: throw MemberNotFoundException()

        if (member.university == null)
                throw UniversityNotFoundException()

        val post = postRepository.findPostById(postId)
                ?: throw PostNotFoundException()

        if (member.university != post.university) {
            throw UniversityNotMatchException()
        }

        if (post.isHide) {
            throw PostHiddenException()
        }

        return PostDetailResponse(
                postId = post.id!!,
                date = post.createdAt!!,
                title = post.title,
                body = post.body,
                memberId = post.author.id!!,
                nickname = post.author.nickname!!,
                profile = post.author.profileImageURL!!,
                categoryId = post.category.id!!,
                price = post.price,
                sellMethod = post.sellMethod,
                paymentMethod = post.paymentMethod,
                isSell = post.isSell,
                isLike = member.scrap.any { it.post == post },
                isWriter = member == post.author,
                images = post.multimedia.map { it.url },
                postHashTagMap = post.postHashTagMap.map {
                    PostDetailResponse.HashTag(
                            hashTagId = it.hashTag.id!!,
                            hashTagName = it.hashTag.tagName
                    )
                }
        )
    }

}