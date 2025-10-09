package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.controller.dto.request.SearchReviewInput
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput

interface ReviewService {

    //리뷰 작성 Service
    fun createReview(createReviewInput : CreateReviewInput) : Boolean

    //리뷰 작성 여부 확인(채팅방 입장 시)
    fun hasWrittenReview(searchReviewInput : SearchReviewInput) : Boolean
}