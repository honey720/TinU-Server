package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.controller.dto.request.SearchReviewInput
import com.tinuproject.tinu.domain.member.repository.ReviewRepository
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
) : ReviewService {
    override fun createReview(createReviewInput: CreateReviewInput): Boolean {
        TODO()
    }

    override fun hasWrittenReview(searchReviewInput: SearchReviewInput): Boolean {
        TODO()
    }

    private fun existReview(userId : UUID, postId: Long){

    }
}