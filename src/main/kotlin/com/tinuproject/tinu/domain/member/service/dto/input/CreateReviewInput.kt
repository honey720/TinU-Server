package com.tinuproject.tinu.domain.member.service.dto.input

import com.tinuproject.tinu.domain.member.enums.Evaluation
import java.util.*

data class CreateReviewInput(
    //평가자 Id
    val reviewerId : UUID,
    //게시글 정보
    val postId : Long,
    
    //메인평가 ( 좋았어요, 보통이에요,별로였어요 )
    val mainEvaluation: Evaluation,

    val isFriendly: Boolean,

    val notLate: Boolean,

    val respondedQuickly: Boolean
) {


}