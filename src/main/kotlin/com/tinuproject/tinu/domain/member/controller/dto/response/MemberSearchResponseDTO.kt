package com.tinuproject.tinu.domain.member.controller.dto.response

import com.tinuproject.tinu.domain.member.entity.Member
import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "멤버 조회 결과")
data class MemberSearchResponseDTO(
    @Schema(description = "UUID로 구성된 UserId 입니다.", defaultValue = "UUID")
    val userId : String,

    @Schema(description = "닉네임", defaultValue = "컴공제일")
    val name : String,

    @Schema(description = "s3 Bucket에서 저장 가능한 Image Url", defaultValue = "URL")
    val profile : String?,

    @Schema(description = "소속 학교", defaultValue = "경기대학교")
    val university : String,

    @Schema(description = "소속 학과", defaultValue = "중고거래할인학과")
    val major : String,

    @Schema(description = "자기소개", defaultValue = "컴퓨터 공학 관련 거래를 자주 합니다!")
    val introduction : String,
    @Schema(description = "학점(거래 평가 점수)", defaultValue = "A+")
    val mark : String
){
    constructor(member: Member) : this(
        userId = member.userId.toString(),
        name = member.nickname!!,
        profile = member.profileImageURL,
        university = member.university!!.name,
        major = member.major!!,
        introduction = member.introduction!!,
        mark = convertMarkToGrade(member.reviewSummary!!.mark)
    )

    companion object {
        fun convertMarkToGrade(mark: Double?): String = when {
            mark == null -> "U"
            mark < 1 -> "U"         // 본 프로젝트에서 평가 되는 mark의 최솟값은 1
            mark < 1.5 -> "F"
            mark < 2.5 -> "C"
            mark < 3.0 -> "B"
            mark < 3.5 -> "B+"
            mark < 4.0 -> "A"
            mark <= 4.5 -> "A+"
            else -> "U"             // 본 프로젝트에서 평가되는 mark의 최댓값을 4.5
        }
    }
}
