package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.socialmember.repository.SocialMemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    val memberRepository: MemberRepository
):MemberService {
    override fun insertEmail(eMail: String) {
        TODO("Not yet implemented")
    }

    override fun insertNickName(name: String) {
        TODO("Not yet implemented")
    }

    override fun findMemberByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun existMemberByEmail(email: String) :Boolean{
        val existMember= memberRepository.findMemberByeMail(email)

        existMember?: return false

        return true
    }
}