package com.tinuproject.tinu.domain.member.dto.client_controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.tinuproject.tinu.s3.dto.S3Verifiable
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "유저 정보 업데이트")
data class UpdateUserInfoRequestDTO(
    @Schema(description = "닉네임", example = "김태완")
    val nickname : String,
    @Schema(description = "자기소개", example = "안녕하세요! 항상 공정한 거래를 약속드립니다.")
    val introduction : String?,
    @Schema(description = "전공", example = "중고거래학과")
    val major : String?,
    @Schema(description = "학년", example = "3")
    val grade : Int,
    @Schema(description = "바꾸는 이미지")
    val profile : Image?
) {

    data class Image(
        @Schema(description = "s3 이미지 Key", example = "Test")
        override val key: String,

        @Schema(description = "s3 이미지 등록시 받아지는 Tag", example = "TestETag")
        override val ETag: String
    ):S3Verifiable
}