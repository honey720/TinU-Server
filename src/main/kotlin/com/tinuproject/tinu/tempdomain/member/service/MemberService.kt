package com.tinuproject.tinu.tempdomain.member.service

import com.tinuproject.tinu.tempdomain.member.dto.response.MemberSearchResponseDTO
import com.tinuproject.tinu.tempdomain.member.dto.request.RegisterRequestDTO
import com.tinuproject.tinu.tempdomain.member.dto.request.UpdateUserInfoRequestDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
interface MemberService {

    fun registerMember(userId: UUID, registerRequestDTO: RegisterRequestDTO)

    fun usableMemberByNickname(userId : UUID, nickName : String) : Boolean

    fun usableMemberByEmail(userId: UUID,email : String) :Boolean

    fun findMemberByUserId(userId: UUID) : MemberSearchResponseDTO

    fun updateMember(userId: UUID, updateUserInfoRequestDTO: UpdateUserInfoRequestDTO)
}