package com.tinuproject.tinu.domain.post.service

import com.tinuproject.tinu.domain.exception.post.MemberNotFoundException
import com.tinuproject.tinu.domain.exception.post.UniversityNotFoundException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.post.dto.response.PostResponseDTO
import com.tinuproject.tinu.domain.post.dto.response.PostsListResponseDTO
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
    ): PostsListResponseDTO {
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
            PostResponseDTO(
                    postId = post.id,
                    createdAt = post.createdAt,
                    title = post.title,
                    price = post.price,
                    thumbnail = post.thumbnail,
                    isLike = member.scrap.any { it.post == post },
                    isSell = post.isSell
            )
        }

        return PostsListResponseDTO(
                posts = posts,
                size = posts.size,
                nextCursorId = nextCursorId
        )
    }


}