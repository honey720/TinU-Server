package com.tinuproject.tinu.domain.member.service.dto.input

import com.tinuproject.tinu.domain.member.enums.Evaluation
import jakarta.persistence.Column
import java.util.*

class CreateReviewInput(
    //평가자 Id
    val reviewerId : UUID,
    //피평가자 Id
    val revieweeId : UUID,
    //메인평가 ( 좋았어요, 보통이에요,별로였어요 )
    val mainEvaluation: Evaluation,

    val isFriendly: Boolean,

    val wasLate: Boolean,

    val respondedQuickly: Boolean
) {


}