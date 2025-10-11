package com.tinuproject.tinu.domain.member.controller.dto.request

import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
@Schema
class CreateReviewRequest(

    //게시글 Id
    @Schema(description = "게시글 Id", example = "UUID")
    val postId : Long,

    //메인평가 ( 좋았어요, 보통이에요,별로였어요 )
    @Schema(description = "메인 평가", example = "GOOD OR SOSO OR BAD")
    val mainEvaluation: Evaluation,

    @Schema(description = "서브 평가(순서: isFriendly, wasLate, respondedQuickly)", example = "[true, false, true]")
    val subEvaluation : List<Boolean>,
) {

    fun of(reviewerId : UUID) : CreateReviewInput{
        return CreateReviewInput(
            reviewerId = reviewerId,
            postId = postId,
            mainEvaluation = this.mainEvaluation,
            isFriendly = this.subEvaluation[0],
            notLate = this.subEvaluation[1],
            respondedQuickly = this.subEvaluation[2]
        )
    }
}