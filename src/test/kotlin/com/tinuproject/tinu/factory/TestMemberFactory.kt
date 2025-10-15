package com.tinuproject.tinu.factory

import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.enums.Social
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.university.entity.University
import java.util.*

object TestMemberFactory {
    fun create(
        memberRepository: MemberRepository,
        university: University,
        nickname: String,
        userId : UUID
    ): Member {

        return memberRepository.save(
            Member(
                userId = userId,
                university = university,
                nickname = nickname,
                major = "컴퓨터 공학부",
                grade = 4,
                profileImageURL = null,
                introduction = "나는야 컴공생~",
                email = "test@kyonggi.ac.kr",
                reportCount = 0,
                social = Social.KAKAO,
            )
        )
    }
}
