package com.tinuproject.tinu.domain.member.dto.client_controller.response

import com.tinuproject.tinu.domain.entity.Member
import java.util.*

data class MemberSearchResponseDTO(
    val userId : String,
    val name : String,
    val profile : String?,
    val university : String,
    val major : String,
    val introduction : String,
    val mark : String
){
    constructor(member: Member) : this(
        userId = member.userId.toString(),
        name = member.nickname!!,
        profile = member.profileImageURL,
        university = member.university!!.name,
        major = member.major!!,
        introduction = member.introduction!!,
        mark = convertMarkToGrade(member.mark!!)
    )

    companion object {
        fun convertMarkToGrade(mark: Double): String {
            return when (mark) {
                in 0.0..0.5 -> "F"
                in 0.5..1.5 -> "C"
                in 1.5..2.5 -> "C+"
                in 2.5..3.0 -> "B"
                in 3.0..3.5 -> "B+"
                in 3.5..4.0 -> "A"
                in 4.0..4.5 -> "A+"
                else -> "Invalid" // 범위를 벗어난 값 방어 로직
            }
        }
    }
}
