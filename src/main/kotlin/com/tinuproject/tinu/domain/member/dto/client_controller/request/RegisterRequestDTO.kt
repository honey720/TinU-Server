package com.tinuproject.tinu.domain.member.dto.client_controller.request

import com.tinuproject.tinu.s3.dto.S3Verifiable

data class RegisterRequestDTO(
    val nickName : String,
    val profile : Image?,
    val major : String,
    val grade : Int,
    val email : String,
    val introduction : String?,
){
    data class Image (
        override val key: String,
        override val ETag: String
    ):S3Verifiable
}
