package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.entity.Member
import com.tinuproject.tinu.domain.member.dto.controller.RegisterRequestDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
interface MemberService {

    fun registerMember(userId: UUID, registerRequestDTO: RegisterRequestDTO)

    fun insertNickName(userId : UUID,name : String)

    fun existMemberByNickName(userId : String, name : String) : Boolean

    fun existMemberByEmail(userId: UUID,email : String) :Boolean

    fun findMemberByUserId(userId : UUID) : String
}