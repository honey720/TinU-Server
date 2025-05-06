package com.tinuproject.tinu.domain.university.service


import com.tinuproject.tinu.domain.university.entity.UniversityDomain
import com.tinuproject.tinu.domain.university.exception.NotExistDomainException
import com.tinuproject.tinu.domain.university.repository.UniversityDomainRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UniversityDomainServiceImpl(
    val universityDomainRepository: UniversityDomainRepository

) : UniversityDomainService {
    @Transactional(readOnly = true)
    override fun existDomain(domain: String) : Boolean{
        val university : UniversityDomain? = universityDomainRepository.findByDomain(domain)

        university?:throw NotExistDomainException()
        return true
    }
}