package com.tinuproject.tinu.domain.universitydomain.repository

import com.tinuproject.tinu.domain.entity.UniversityDomain
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UniversityDomainRepository : CrudRepository<UniversityDomain,Long> {
    fun existsByDomain(domain : String) : Boolean

    fun findByDomain(domain : String) : UniversityDomain?
}