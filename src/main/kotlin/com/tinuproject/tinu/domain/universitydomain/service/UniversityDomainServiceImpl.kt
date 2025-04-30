package com.tinuproject.tinu.domain.universitydomain.service


import com.tinuproject.tinu.domain.entity.UniversityDomain
import com.tinuproject.tinu.domain.exception.university.NotExistDomainException
import com.tinuproject.tinu.domain.universitydomain.repository.UniversityDomainRepository
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