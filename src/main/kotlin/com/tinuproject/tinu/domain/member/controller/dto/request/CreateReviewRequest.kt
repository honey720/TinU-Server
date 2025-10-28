package com.tinuproject.tinu.domain.member.controller.dto.request

import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
@Schema
data class CreateReviewRequest(

    //게시글 Id
    @Schema(description = "게시글 Id", example = "1")
    val postId : Long,

    //메인평가 ( 좋았어요, 보통이에요,별로였어요 )
    @Schema(description = "메인 평가", example = "GOOD OR SOSO OR BAD")
    val mainEvaluation: Evaluation,

    @Schema(description = "대화가 친절했어요", example = "true or false")
    val isFriendly : Boolean,

    @Schema(description = "시간을 준수했어요.", example = "true or false")
    val notLate : Boolean,

    @Schema(description = "응답이 빨랐어요", example = "true or false")
    val respondedQuickly : Boolean
) {

    fun of(reviewerId : UUID) : CreateReviewInput{
        return CreateReviewInput(
            reviewerId = reviewerId,
            postId = postId,
            mainEvaluation = this.mainEvaluation,
            isFriendly = this.isFriendly,
            notLate = this.notLate,
            respondedQuickly = this.respondedQuickly
        )
    }
}