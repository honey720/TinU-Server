package com.tinuproject.tinu.domain.member.dto.client_controller.request

import com.tinuproject.tinu.s3.dto.request.S3VerifiableRequest


data class UpdateUserInfoRequestDTO(
    val nickname : String,
    val introduction : String?,
    val major : String?,
    val grade : Int?,
    val profile : S3VerifiableRequest?
)