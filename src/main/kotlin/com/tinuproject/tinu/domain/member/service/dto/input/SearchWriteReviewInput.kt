package com.tinuproject.tinu.domain.member.service.dto.input

import java.util.*

data class SearchWriteReviewInput(
    val userId : UUID,
    val postId : Long
) {
}