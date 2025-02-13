package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.domain.entity.Member
import org.springframework.data.repository.CrudRepository

interface MemberRepository : CrudRepository<Member, Long> {
    fun findMemberByeMail(eMail: String): Member?
}