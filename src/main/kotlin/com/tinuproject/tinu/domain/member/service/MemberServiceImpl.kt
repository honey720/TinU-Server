package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.enum.Gender
import com.tinuproject.tinu.domain.exception.common.UnauthorizedAccessException
import com.tinuproject.tinu.domain.exception.member.ExistEmailException
import com.tinuproject.tinu.domain.exception.member.ExistNameException
import com.tinuproject.tinu.domain.exception.member.NotExistMemberException
import com.tinuproject.tinu.domain.exception.university.NotExistDomainException
import com.tinuproject.tinu.domain.member.dto.controller.RegisterRequestDTO
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.socialmember.repository.SocialMemberRepository
import com.tinuproject.tinu.domain.university.repository.UniversityRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberServiceImpl(
    val memberRepository: MemberRepository,
    val universityRepository: UniversityRepository
):MemberService {


    override fun registerMember(userId: UUID, registerRequestDTO: RegisterRequestDTO) {
        val member = memberRepository.findMemberByUserId(userId)
        val university = universityRepository.findByDomain(registerRequestDTO.eMail.split("@")[1])
        member?: throw NotExistMemberException()
        university ?: throw NotExistDomainException()
        if(member.eMail!=registerRequestDTO.eMail||member.nickname!=registerRequestDTO.nickName) throw UnauthorizedAccessException()

        member.nickname = registerRequestDTO.nickName
        member.major = registerRequestDTO.major
        member.profileImageURL = registerRequestDTO.profileImageURL

        if(registerRequestDTO.gender=="man") member.gender = Gender.MAN
        else member.gender = Gender.WOMEN
        member.eMail = registerRequestDTO.eMail
        member.university = university
        member.introduction = registerRequestDTO.introduction

        memberRepository.save(member)
    }

    override fun insertNickName(userId:UUID,name: String) {
        val member = memberRepository.findMemberByUserId(userId)


        member?: throw NotExistMemberException()
        member.nickname = name
        memberRepository.save(member)
    }

    override fun usableMemberByNickName(userId : UUID, name: String) :Boolean{
        val existMember = memberRepository.findMemberByNickname(name)

        if(existMember !=null&&userId!=existMember.userId) throw ExistNameException()

        return true
    }

    override fun usableMemberByEmail(userId: UUID,email: String) :Boolean{
        val existMember= memberRepository.findMemberByeMail(email)

        if(existMember!=null&&existMember.userId!=userId) throw ExistEmailException()

        return true
    }

    override fun findMemberByUserId(userId: UUID): String {
        val member = memberRepository.findMemberByUserId(userId)

        return member!!.userId.toString()
    }
}