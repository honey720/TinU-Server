package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.controller.dto.request.SearchReviewInput
import com.tinuproject.tinu.domain.member.entity.Review
import com.tinuproject.tinu.domain.member.exception.ExistReviewException
import com.tinuproject.tinu.domain.member.exception.NotExistMemberException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.member.repository.ReviewRepository
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import com.tinuproject.tinu.domain.post.exception.PostNotFoundException
import com.tinuproject.tinu.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val postRepository : PostRepository
) : ReviewService {
    @Transactional
    override fun createReview(createReviewInput: CreateReviewInput): Boolean {
        if(existReview(userId = createReviewInput.reviewerId, postId = createReviewInput.postId)){
            throw ExistReviewException()
        }


        val reviewer = memberRepository.findMemberByUserId(createReviewInput.reviewerId)?:throw NotExistMemberException()

        val reviewee = memberRepository.findMemberByUserId(createReviewInput.revieweeId)?:throw NotExistMemberException()

        val post = postRepository.findPostById(createReviewInput.postId)?: throw PostNotFoundException()

        reviewRepository.save(Review(
            reviewer = reviewer,
            reviewee = reviewee,
            post = post,
            mainEvaluation = createReviewInput.mainEvaluation,
            isFriendly = createReviewInput.isFriendly,
            wasLate = createReviewInput.notLate,
            respondedQuickly = createReviewInput.respondedQuickly
        ))

        return true
    }

    override fun hasWrittenReview(searchReviewInput: SearchReviewInput): Boolean {
        TODO()
    }

    private fun existReview(userId : UUID, postId: Long) : Boolean{
        return reviewRepository.existsByReviewer_UserIdAndPost_Id(reviewerId = userId, postId = postId)
    }
}