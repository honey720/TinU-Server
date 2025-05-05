package com.tinuproject.tinu.tempdomain.university.service


import com.tinuproject.tinu.tempdomain.university.entity.UniversityDomain
import com.tinuproject.tinu.tempdomain.university.exception.NotExistDomainException
import com.tinuproject.tinu.tempdomain.university.repository.UniversityDomainRepository
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