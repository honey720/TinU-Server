package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import com.tinuproject.tinu.domain.member.service.dto.input.SearchWriteReviewInput

interface ReviewService {

    //리뷰 작성 Service
    fun createReview(input : CreateReviewInput) : Boolean

    //리뷰 작성 여부 확인(채팅방 입장 시)
    fun needWrittenReview(input: SearchWriteReviewInput) : Boolean
}