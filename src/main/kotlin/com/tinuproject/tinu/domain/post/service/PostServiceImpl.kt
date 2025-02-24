package com.tinuproject.tinu.domain.post.service

import com.tinuproject.tinu.domain.entity.Post
import com.tinuproject.tinu.domain.exception.post.MemberNotFoundException
import com.tinuproject.tinu.domain.exception.post.UniversityNotFoundException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.post.repository.PostQueryRepositoryImpl
import com.tinuproject.tinu.domain.post.repository.PostRepository
import com.tinuproject.tinu.security.jwt.JwtUtil
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import java.util.*

const val SIZE = 20
@Service
class PostServiceImpl(
        private val memberRepository: MemberRepository,
        private val postRepository: PostRepository,
        private val postQueryRepositoryImpl: PostQueryRepositoryImpl
): PostService {
    override fun getPostList(
            userId: UUID,
            cursorId: Long?,
            keyword: String?,
            category: List<Long>?,
            minPrice: Int?,
            maxPrice: Int?,
            onlySell: Boolean,
            orderBy: String
    ): Slice<Post> {
        val member = memberRepository.findMemberByUserId(userId)
                ?: throw MemberNotFoundException()

        val university = member.university
                ?: throw UniversityNotFoundException()

        val posts = postQueryRepositoryImpl.findPosts(
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

        var hasNext = false

        if (posts.size > SIZE) {
            posts.subList(0, SIZE)
            hasNext = true
        }

        return SliceImpl(posts, Pageable.ofSize(SIZE), hasNext)
    }
}