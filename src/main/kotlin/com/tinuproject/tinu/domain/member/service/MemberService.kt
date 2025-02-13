package com.tinuproject.tinu.domain.member.service

import org.springframework.stereotype.Service

@Service
interface MemberService {

    fun insertEmail(eMail : String)

    fun insertNickName(name : String)

    fun findMemberByName(name : String)

    fun existMemberByEmail(email : String) :Boolean
}