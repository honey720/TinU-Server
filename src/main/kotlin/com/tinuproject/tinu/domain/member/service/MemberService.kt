package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.entity.Member
import org.springframework.stereotype.Service
import java.util.*

@Service
interface MemberService {


    fun insertEmail(eMail : String)

    fun insertNickName(name : String)

    fun findMemberByName(name : String)

    fun existMemberByEmail(email : String) :Boolean

    fun findMemberByUserId(userId : UUID) : String
}