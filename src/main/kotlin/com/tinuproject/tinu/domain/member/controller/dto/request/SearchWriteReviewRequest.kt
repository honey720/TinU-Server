package com.tinuproject.tinu.domain.member.controller.dto.request

import com.tinuproject.tinu.domain.member.service.dto.input.SearchWriteReviewInput
import java.util.*

class SearchWriteReviewRequest(

    val postId : Long
) {
    fun of(reviewrId : UUID) : SearchWriteReviewInput{
        return SearchWriteReviewInput(
            userId = reviewrId,
            postId = this.postId
        )
    }
}