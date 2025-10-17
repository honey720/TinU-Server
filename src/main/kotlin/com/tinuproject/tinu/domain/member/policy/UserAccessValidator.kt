package com.tinuproject.tinu.domain.member.policy

import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.exception.NotExistMemberException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.global.exception.BadRequestException
import com.tinuproject.tinu.global.exception.ForbiddenException
import org.springframework.stereotype.Component
import java.util.*

object UserAccessValidator{
    fun validateSameUniversity(requestMember: Member, targetMember: Member){
        //동일 유저라면 동일 대학임으로 배제
        if(requestMember.id == targetMember.id){
            return
        }

        val requestUserUniversity = requestMember.university?:throw BadRequestException()
        val targetUserUniversity = targetMember.university?: throw BadRequestException()

        if (requestUserUniversity.id !=targetUserUniversity.id) {
            throw ForbiddenException()
        }
    }
}