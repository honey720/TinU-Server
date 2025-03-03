package com.tinuproject.tinu.domain.post.service

import com.tinuproject.tinu.domain.entity.HashTag
import com.tinuproject.tinu.domain.entity.Multimedia
import com.tinuproject.tinu.domain.entity.Post
import com.tinuproject.tinu.domain.exception.post.*
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.post.dto.request.PostCreateRequest
import com.tinuproject.tinu.domain.post.dto.response.PostDetailResponse
import com.tinuproject.tinu.domain.post.dto.response.PostsListResponse
import com.tinuproject.tinu.domain.post.repository.*
import com.tinuproject.tinu.s3.service.S3Service
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

const val SIZE = 20
@Service
class PostServiceImpl(
        private val memberRepository: MemberRepository,
        private val postRepository: PostRepository,
        private val postQueryRepository: PostQueryRepository,
        private val categoryRepository: CategoryRepository,
        private val hashTagRepository: HashTagRepository,
        private val s3Service: S3Service
): PostService {

    @Transactional(readOnly = true)
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

        println("University ID: ${university.id}")
        println("Params: cursorId=$cursorId, keyword=$keyword, category=$category, minPrice=$minPrice, maxPrice=$maxPrice, onlySell=$onlySell, orderBy=$orderBy")

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

    @Transactional(readOnly = true)
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
                profile = post.author.profileImageURL,
                categoryId = post.category.id!!,
                price = post.price,
                sellMethod = post.sellMethod.toSet(),
                paymentMethod = post.paymentMethod.toSet(),
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

    @Transactional
    override fun createPost(userId: UUID, postCreateRequest: PostCreateRequest): Long {
        val member = memberRepository.findMemberByUserId(userId)
                ?: throw MemberNotFoundException()

        val university = member.university
                ?: throw UniversityNotFoundException()

        val category = categoryRepository.findCategoryById(postCreateRequest.categoryId)
                ?: throw CategoryNotFoundException()

        val urls = s3Service.verifyImage(postCreateRequest.images)

        val newPost = Post(
                university = university,
                title = postCreateRequest.title,
                body = postCreateRequest.body,
                author = member,
                buyer = null,
                category = category,
                price = postCreateRequest.price,
                sellMethod = setOf(postCreateRequest.sellMethod),
                isSell = true,
                isHide = false,
                paymentMethod = setOf(postCreateRequest.paymentMethod),
                thumbnail = urls[0],
                reportCount = 0,
                scrapCount = 0,
                multimedia = mutableListOf(),
                postHashTagMap = mutableListOf(),
                scrap = mutableListOf()
        )

        println("멀티미디어 추가")
        urls.forEach { url ->
            val multimedia = Multimedia(url = url, isImage = false, post = newPost)
            newPost.multimedia.add(multimedia)
        }
        
        println("해시태그 추가")
        val hashTagList = hashTagRepository.saveAll(
                postCreateRequest.hashTag.map { tagName ->
                    hashTagRepository.findHashTagByTagName(tagName) ?: HashTag(tagName = tagName)
                }
        )

        println("게시글해시태그맵 추가")
        hashTagList.forEach { hashTag ->
            newPost.addHashTag(hashTag)
        }

        postRepository.save(newPost)

        return newPost.id!!
    }
}