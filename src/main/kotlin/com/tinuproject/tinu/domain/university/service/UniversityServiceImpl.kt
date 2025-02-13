package com.tinuproject.tinu.domain.university.service

import com.tinuproject.tinu.domain.entity.University
import com.tinuproject.tinu.domain.university.repository.UniversityRepository
import org.springframework.stereotype.Service

@Service
class UniversityServiceImpl(
    val universityRepository: UniversityRepository
) : UniversityService {
    override fun existDomain(domain: String) : Boolean{
        val university : University? = universityRepository.findByDomain(domain)

        return university != null
    }
}