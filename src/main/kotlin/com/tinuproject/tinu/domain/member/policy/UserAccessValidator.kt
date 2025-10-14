package com.tinuproject.tinu.domain.member.policy

import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.exception.NotExistMemberException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.global.exception.UnauthorizedAccessException
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAccessValidator(
    private val memberRepository: MemberRepository
){
    fun validateSameUniversity(requestUserId: UUID, targetUserId: UUID) : Member {
        if(requestUserId==targetUserId){
            return memberRepository.findMemberByUserId(requestUserId) ?: throw NotExistMemberException()
        }

        //각각 멤버를 따로 조회 하는 것이 아닌 한번의 쿼리로 2개를 검색.
        val members = memberRepository.findByUserIdIn(listOf(requestUserId, targetUserId))

        if (members.size < 2) throw NotExistMemberException()
        val requestMember  = members.find { it.userId == requestUserId } ?: throw NotExistMemberException()
        val targetMember  = members.find { it.userId == targetUserId } ?: throw NotExistMemberException()

        if (requestMember.university?.id != targetMember.university?.id) {
            throw UnauthorizedAccessException()
        }

        return targetMember
    }
}