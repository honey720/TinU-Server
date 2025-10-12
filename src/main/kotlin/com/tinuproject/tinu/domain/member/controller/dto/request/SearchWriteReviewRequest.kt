package com.tinuproject.tinu.domain.member.controller.dto.request

import com.tinuproject.tinu.domain.member.service.dto.input.SearchWriteReviewInput
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "리뷰 작성 필요 여부 확인")
class SearchWriteReviewRequest(
    @Schema(description = "거래 완료된 채팅방의 post Id")
    val postId : Long
) {
    fun of(reviewerId : UUID) : SearchWriteReviewInput{
        return SearchWriteReviewInput(
            userId = reviewerId,
            postId = this.postId
        )
    }
}