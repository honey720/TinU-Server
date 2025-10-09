package com.tinuproject.tinu.domain.member.controller.dto.request

import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import java.util.*

class CreateReviewRequest(
    //피평가자 Id
    val revieweeId : UUID,
    //메인평가 ( 좋았어요, 보통이에요,별로였어요 )
    val mainEvaluation: Evaluation,

    val isFriendly: Boolean,

    val wasLate: Boolean,

    val respondedQuickly: Boolean
) {

    fun of(reviewerId : UUID) : CreateReviewInput{
        return CreateReviewInput(
            reviewerId = reviewerId,
            revieweeId = this.revieweeId,
            mainEvaluation = this.mainEvaluation,
            isFriendly = this.isFriendly,
            wasLate = this.wasLate,
            respondedQuickly = this.respondedQuickly
        )
    }
}